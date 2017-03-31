# API
Repository for APIs

# File Management
REST APIs:
1. Upload a file with a few metadata fields
POST /rest/v1/files
2. Get file metadata
GET /rest/v1/files/{fileId}
3. Download content stream
GET rest/v1/files/{fileId}/content
4. Search for file IDs with a search criterion(author)
GET /rest/v1/files?field=id&author=ada