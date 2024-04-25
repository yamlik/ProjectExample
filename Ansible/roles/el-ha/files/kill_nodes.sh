#!/bin/bash
cat `find /opt/TEST/TEST/control/*/*/control/*/pidfile` | xargs -n1 kill -9 || true
