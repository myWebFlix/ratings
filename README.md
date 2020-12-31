# Video Rating Microservice

## Google Cloud Storage Authentication

On Kubernetes:

```bash
kubectl create secret generic gcloud-storage-key --from-file=key.json=PATH_TO_KEY.json
```

Locally:

```bash
GOOGLE_APPLICATION_CREDENTIALS=PATH_TO_KEY.json
```
