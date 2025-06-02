FROM hashicorp/consul:latest

RUN apk add --no-cache curl bash

COPY consul/kv.json /consul/kv.json
COPY consul/entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]