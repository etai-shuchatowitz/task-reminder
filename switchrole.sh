#!/bin/bash

ARN=$1

if [ -n "$ARN" ]; then
  echo "[$ARN]"
  RESPONSE=$(
  aws sts assume-role \
  --role-arn $ARN \
  --role-session-name "RoleSession1" \
  --profile default \
  --output text \
  --query 'Credentials')

  AWS_ACCESS_KEY_ID=$(echo $RESPONSE | awk '{print $1}')
  AWS_SESSION_EXPIRATION=$(echo $RESPONSE | awk '{print $2}')
  AWS_SECRET_ACCESS_KEY=$(echo $RESPONSE | awk '{print $3}')
  AWS_SESSION_TOKEN=$(echo $RESPONSE | awk '{print $4}')

  export AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
  export AWS_SESSION_EXPIRATION=$AWS_SESSION_EXPIRATION
  export AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
  export AWS_SESSION_TOKEN=$AWS_SESSION_TOKEN
fi