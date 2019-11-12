#!/bin/bash

set -e
set -x

source stratio/b-log.sh
source stratio/kms_utils.sh

## VAULT LOGIN =====================================================

    DOCKER_LOG_LEVEL=${DOCKER_LOG_LEVEL:-DEBUG}
    eval LOG_LEVEL_${DOCKER_LOG_LEVEL}
    B_LOG --stdout true # enable logging over stdout

    export PORT0=${PORT0:-"8080"}

    declare -a VAULT_HOSTS
    IFS_OLD=$IFS
    IFS=',' read -r -a VAULT_HOSTS <<< "$VAULT_HOST"

    declare -a MARATHON_ARRAY
    OLD_IFS=$IFS
    IFS='/' read -r -a MARATHON_ARRAY <<< "$MARATHON_APP_ID"
    IFS=$OLD_IFS

    INFO "Trying to login in Vault"
    # Approle login from role_id, secret_id
    if [ "xxx$VAULT_TOKEN" == "xxx" ];
    then
       INFO "Login in vault..."
       login
       if [[ ${code} -ne 0 ]];
       then
           ERROR "  - Something went wrong log in in vault. Exiting..."
           return ${code}
       fi
    fi
    INFO "  - Logged!"

    export JAVA_ARGS="--server.port=${PORT0}"


## SERVICE NAME ====================================================

    export MARATHON_SERVICE_NAME=${MARATHON_ARRAY[-1]}
    MARATHON_SERVICE_NAME=$(echo $MARATHON_SERVICE_NAME | sed -E 's/(.*)-\d*$/\1/')


## BASIC AUTHENTICATION WITH CONFIG-SERVER =========================

#    CONFIG_SERVER_ID=${CONFIG_SERVER_NAME//-/_}
#    CONFIG_SERVER_ID=${CONFIG_SERVER_ID^^}
#
#    getPass userland ${CONFIG_SERVER_NAME} basic
#
#    export BASIC_USER_VAR=${CONFIG_SERVER_ID}_BASIC_USERNAME
#    export BASIC_PASS_VAR=${CONFIG_SERVER_ID}_BASIC_PASSWORD
#
#    BASIC_USER_VAR=${BASIC_USER_VAR^^}
#    BASIC_PASS_VAR=${BASIC_PASS_VAR^^}
#
#    export VAULT_BASIC_USERNAME=${!BASIC_USER_VAR}
#    export VAULT_BASIC_PASSWORD=${!BASIC_PASS_VAR}


## POSTGRES DB =====================================================

    INFO "Postgres connection config start"
#    export POSTGRES_URL="jdbc:postgresql://barclaysventures-poolretail.barclaysventures.marathon.mesos:5432/ontology"
    export SPRING_DATASOURCE_USERNAME="${MARATHON_SERVICE_NAME}_barclaysventures"

    export POSTGRES_CERT="/etc/stratio/${MARATHON_SERVICE_NAME}_barclaysventures.pem"
    export POSTGRES_KEY="/etc/stratio/key.pkcs8"
    export CA_BUNDLE_PEM="/etc/stratio/ca-bundle.pem"

    INFO "  - Postgres certificate path ${POSTGRES_CERT}"
    INFO "  - Retreiving service certificate [PEM] from url: /${MARATHON_SERVICE_NAME} "
    getCert "userland" \
                "${MARATHON_SERVICE_NAME}.barclaysventures" \
                "${MARATHON_SERVICE_NAME}_barclaysventures" \
                "PEM" \
                "/etc/stratio" \
    && INFO "    - OK" \
    || INFO "    - Error"

    INFO "  - Getting Ca-Bundle for a given SSL_CERT_PATH/ca-bundle.pem"
    getCAbundle "/etc/stratio" "PEM" \
        && INFO "    - OK:."   \
        || INFO "    - Error."

    openssl pkcs8 -topk8 -inform pem -in /etc/stratio/"${MARATHON_SERVICE_NAME}_barclaysventures".key -outform der -nocrypt -out ${POSTGRES_KEY}

    export SPRING_DATASOURCE_URL="${POSTGRES_URL}?prepareThreshold=0&ssl=true&sslmode=verify-full&sslcert=${POSTGRES_CERT}&sslrootcert=${CA_BUNDLE_PEM}&sslkey=${POSTGRES_KEY}"
    INFO "  - SPRING_DATASOURCE_URL:  "
    INFO "    - ${SPRING_DATASOURCE_URL}"


## KAFKA SSL COLLECTIONS ===========================================

    getCert "userland" \
                "${MARATHON_SERVICE_NAME}.barclaysventures" \
                "${MARATHON_SERVICE_NAME}_barclaysventures" \
                "JKS" \
                "/etc/stratio" \
    && INFO "OK: Getting ${MARATHON_SERVICE_NAME} certificate for kafka"   \
    ||  INFO "Error: Getting ${MARATHON_SERVICE_NAME} certificate for kafka"

    CERTIFICATE_VAR_PASS=${MARATHON_SERVICE_NAME//-/_}"_barclaysventures_KEYSTORE_PASS"
    CERTIFICATE_VAR_PASS=${CERTIFICATE_VAR_PASS^^}
    export CERTIFICATE_KEYSTORE_PASSWORD_VARIABLE=${!CERTIFICATE_VAR_PASS}
    export SPRING_KAFKA_SSL_KEYSTORE_PASSWORD=${CERTIFICATE_KEYSTORE_PASSWORD_VARIABLE}
    export SPRING_KAFKA_SSL_KEYPASSWORD=${CERTIFICATE_KEYSTORE_PASSWORD_VARIABLE}
    export SPRING_KAFKA_PROPERTIES_SSL_KEYSTORE_LOCATION=/etc/stratio/kafka.pkcs12
    export SPRING_KAFKA_PROPERTIES_SSL_KEYSTORE_TYPE=PKCS12
    export SPRING_KAFKA_PROPERTIES_SECURITY_PROTOCOL=SSL
#    export SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka-0-broker.barclaysventures-kafka.mesos:9092,kafka-1-broker.barclaysventures-kafka.mesos:9092,kafka-2-broker.barclaysventures-kafka.mesos:9092

    openssl pkcs12 -export -out /etc/stratio/kafka.pkcs12 \
    -in /etc/stratio/"${MARATHON_SERVICE_NAME}_barclaysventures".pem \
    -inkey /etc/stratio/"${MARATHON_SERVICE_NAME}_barclaysventures".key \
    -passout pass:${SPRING_KAFKA_SSL_KEYSTORE_PASSWORD}

## TRUSTSTORE  ====================================================

    getCAbundle "/data/resources" "PEM" \
    && INFO "OK: Getting ca-bundle" \
    || INFO "Error: Getting ca-bundle"

    ${JAVA_HOME}/bin/keytool -noprompt -import -storepass changeit -file /data/resources/ca-bundle.pem -cacerts -alias ca

    export SPRING_KAFKA_PROPERTIES_SSL_TRUSTSTORE_LOCATION=$JAVA_HOME/lib/security/cacerts
    export SPRING_KAFKA_SSL_TRUSTSTORE_PASSWORD=changeit


## RUN JAVA APP ===================================================

    HEAP_PERCENTAGE=${HEAP_PERCENTAGE:-"80"}
    JAVA_TOOL_OPTIONS=${JAVA_TOOL_OPTIONS:-"-XX:+UseG1GC -XX:MaxRAMPercentage=${HEAP_PERCENTAGE} -XshowSettings:vm"}

    JAVA_CMD="java ${JAVA_TOOL_OPTIONS} -jar /data/app.jar ${JAVA_ARGS}"
    INFO ${JAVA_CMD}

    INFO
    INFO "Starting Spring Boot Service !"
    INFO

    ${JAVA_CMD}
