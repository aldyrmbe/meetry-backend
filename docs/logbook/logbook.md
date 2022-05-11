# Meetry API Specifications

## API List

- Add logbook
- Delete logbook
- Edit logbook
- Get logbook by id
- Get logbooks
- Add comment

## Add logbook
- Endpoint: `/api/logbook/{proyekId}/{subFolderId}`
- HTTP Method: `POST`
- Request body
  ```json
  {
    "judul": "Judul",
    "waktu": 12121212122,
    "deskripsi": "Isi logbook",
    "tags": ["Tags1", "Tags2", "Tags3"]
  }
  ```
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Logbook berhasil ditambahkan."
  } 
  ```

## Delete logbook
- Endpoint: `/api/logbook/{proyekId}/{logbookId}`
- HTTP Method: `DELETE`
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Logbook berhasil dihapus."
  } 
  ```

## Edit logbook
- Endpoint: `/api/logbook/{proyekId}/{logbookId}`
- HTTP Method: `POST`
- Request body
  ```json
  {
    "judul": "Judul",
    "waktu": 12121212122,
    "isi": "Isi logbook",
    "tags": ["Tags1", "Tags2", "Tags3"]
  }
  ```
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Logbook berhasil diedit."
  } 
  ```

## Get logbook by id
- Endpoint: `/api/logbook/{proyekId}/{logbookId}`
- HTTP Method: `GET`
- Response body:
  ```json
  {
    "code": 200,
    "status": "OK",
    "data": {
      "id": "logbookID",
      "judul": "Judul 1",
      "deskripsi": "Deskripsi",
      "waktu": 121212121221,
      "tags": ["Tags 1", "Tags 2", "Tags 3"]
    } 
  }
  ```
  
## Get Logbooks

- Endpoint: `/api/logbook/{proyekId}/{subFolderId}/getLogbooks`
- Request param: `page`: int
- HTTP Method: `GET`
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "data": [
       {
         "id": "logbookId",
         "sender":  {
            "nama": "Nama",
            "url": "www.google.com/poto" 
          },
         "createdAt": 12121212121, 
         "judul": "Judul",
         "waktu": 12121211212,
         "deskripsi": "Isi Logbook",
         "tags": ["tag 1", "tag 2", "tag 3"],
         "comments": [
           {
             "pengirim": "Sender",
             "waktu": 1212121221,
             "isi": "Isi Komentar"
           },
           {
             "pengirim": "Sender",
             "waktu": 1212121221,
             "isi": "Isi Komentar"
           }
         ]
       }
    ]
    
  } 
  ```
  
## Add comment
- Endpoint: `/api/logbook/{proyekId}/{logbookId}/comment`
- HTTP Method: `POST`
- Request body:
```json
  {
    "content": "Ini komentar" 
  }
  ```
- Response body:
  ```json
  {
    "code": 200,
    "status": "OK",
    "message": "Komentar berhasil ditambahkan."
  }
  ```