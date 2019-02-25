# taskName-reminder

aws cloudformation package --template-file template.yaml --s3-bucket etai-cloudformation-templates --s3-prefix "taskName-reminder" --output-template-file packaged-template.yaml --profile etai
aws cloudformation deploy --template-file packaged-template.yaml --stack-name taskName-reminder --profile etai