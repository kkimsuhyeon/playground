#!/bin/sh

# 기본값 지정 (옵션)
: "${CONSUL_BIND_INTERFACE:=eth0}"
: "${CONSUL_CLIENT_INTERFACE:=eth0}"

# 실제 IP 주소 추출
BIND_ADDR="0.0.0.0"
CLIENT_ADDR="0.0.0.0"

# Consul 실행 (백그라운드)
consul agent -server -bootstrap-expect=1 -ui -node=consul-server \
  -bind="$BIND_ADDR" -client="$CLIENT_ADDR" \
  -data-dir=/consul/data -config-dir=/consul/config \
  -enable-script-checks=true -log-level=info &

# Consul 리더가 될 때까지 대기
until curl -sf http://localhost:8500/v1/status/leader > /dev/null; do
  echo "[INFO] Waiting for Consul HTTP API..."
  sleep 1
done

until [ "$(curl -s http://localhost:8500/v1/status/leader)" != "\"\"" ]; do
  echo "[INFO] Waiting for Consul leader election..."
  sleep 1
done

echo "[INFO] Consul is ready ✅"

# KV import
cat /consul/kv.json | consul kv import -

# Consul agent 유지
wait