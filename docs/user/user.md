## Add Account Officer

- Endpoint: `/user/addAccountOfficer`
- Request body:
  ```json
  {
    "name": "Nama Lengkap",
    "email": "email@mail.com",
    "password": "test123"
  }
  ```
- Response (success):
  ```json
  {
    "code": 200,
    "status": "OK",
    "message": "Account officer berhasil dibuat."
  }
  ```

## Get Notification

- Endpoint: `/user/notification`
- Request param: `page`: int
- Response:
  ```json
  {
    "code": 200,
    "status": "OK",
    "data": [
      {
        "createdAt": 12121212,
        "title": "Title Notifikasi",
        "description": "Description Notifikasi"
      },
      {
        "createdAt": 12121212,
        "title": "Title Notifikasi",
        "description": "Description Notifikasi"
      },
      {
        "createdAt": 12121212,
        "title": "Title Notifikasi",
        "description": "Description Notifikasi"
      }  
    ]  
  }
  ```