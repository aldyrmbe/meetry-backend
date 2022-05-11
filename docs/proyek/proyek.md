# Meetry API Specifications

## API List

- Ajukan Proyek
- Get Proyek List
- Get Detail Proyek
- Get Subfolder
- Add Subfolder
- Delete SubFolder
- Edit SubFolder
- Add Logbook
- Delete Logbook
- Edit Logbook
- Set Proyek on Discussion
- Activate Proyek
- Close Proyek
- Cancel Proyek
// Later
- Add Comment

## Ajukan Proyek

- Endpoint: `/api/proyek`
- HTTP method: `POST`
- Request header:
    - Content-Type: `multipart/form-data`
- Request body:
  | Key | Value | Type |
  | --- | ----------- | --- |
  | `data`| Data terkait proyek | `string`
  | `files` | Dokumen pendukung | `file[]`

  Example  
  `data`:

  ```
  "{
    "linkPendukung": ["www.google.com", "www.facebook.com"],
    "kebutuhanProyek": [
        {
            "kebutuhanProyek": "Test Kebutuhan Proyek",
            "bentukKolaborasi": "Test Bentuk Kolaborasi",
            "penjelasanTambahan": "Test Penjelasan Tambahan"
        }
    ],
    "judul": "Test Judul",
    "periodeMulai": 1649523600000,
    "periodeSelesai": 1646845200000,
    "bidang": "Test Bidang",
    "latarBelakang": "Test Latar Belakang",
    "tujuan": "Test Tujuan",
    "sasaranPengguna": "Test Sasaran",
    "output": "Test Output",
    "kebermanfaatanProduk": "Test Kebermanfaatan Produk",
    "indikatorKesuksesan": "Test Indikator Kesuksesan",
    "tingkatKesiapan": "Test Tingkat Kesiapan"
  }"

  ```

## Get Proyek List

- Endpoint: `/api/proyek`
- HTTP method: `GET`
   Request param:

    | Variable| Description | Type | 
    | --- | ----------- | --- | 
    | `status` | Status proyek, terdiri atas `DALAM_PENGAJUAN`, `DALAM_DISKUSI`, `AKTIF`, `SELESAI`, dan `DIBATALKAN` | `string`
    | `searchQuery` | Query untuk mencari nama proyek | `string`
    | `page` | Halaman untuk pagination | `int`
- Response body (Success):
  ```json
  {
    "code": 200,
    "status": "OK",
    "data": [
      {
        "id": "proyekId",
        "judul": "Judul 2",
        "partisipan": [],
        "status": "DALAM_PENGAJUAN"
      },
      {
        "id": "proyekId",
        "judul": "Judul",
        "partisipan": ["Microsoft Indonesia"],
        "status": "DITEMUKAN"
      },
    ]
  }
  ```

## Get Detail Proyek
- Endpoint: `/api/proyek/{proyekId}`
- HTTP Method: `GET`
- Response body (Success):
  ```json
  {
    "code": 200,
    "status": "OK",
    "data": {
      "pemohon": "PENELIITI | MITRA",
      "overviewProyek": {
        "judul": "Judul Proyek",
        "partisipan": {
          "mitra": [{
            "url": "www.photo.com",
            "nama": "Mitra 1"
          }],
          "peneliti": [{
            "url": "www.photo.com",
            "nama": "Peneliti 1"
          }],
          "accountOfficer": {
            "url": "www.photo.com",
            "nama": "Account Officer 1"
          }
        },
        "periode": "1 November 2021 - 1 Mei 2021 (6 bulan)",
        "latarBelakang": "Test Latar Belakang",
        "tujuan": "Test Tujuan",
        "sasaranPengguna": "Test Sasaran",
        "output": "Test Output",
        "kebermanfaatanProduk": "Test Kebermanfaatan Produk",
        "indikatorKesuksesan": "Test Indikator Kesuksesan",
        "tingkatKesiapan": "Test Tingkat Kesiapan",
        "linkPendukung": [
          {
            "nama": "Link 1", 
            "value": "www.google.com"
          },
          {
            "nama": "Link 2",
            "value": "www.google.com"
          }
        ],
        "dokumenPendukung": [
          {
            "nama": "File 1",
            "value": "www.google.com"
          },
          {
            "nama": "File 2",
            "value": "www.google.com"
          }
        ],
        "whatsappGroupLink": "www.whatsapp.com"
      },
      "kebutuhanProyek": [
        {
          "kebutuhanProyek": "Test Kebutuhan Proyek",
          "bentukKolaborasi": "Test Bentuk Kolaborasi",
          "penjelasanTambahan": "Test Penjelasan Tambahan",
          "partisipan": "Google Indonesia"
        },
        {
          "kebutuhanProyek": "Test Kebutuhan Proyek 2",
          "bentukKolaborasi": "Test Bentuk Kolaborasi 2",
          "penjelasanTambahan": "Test Penjelasan Tambahan 2",
          "partisipan": "Microsoft Indonesia"
        }
      ],
      "folders": [
        {
          "id": "folderId",
          "namaFolder": "Folder 1"
        },
        {
          "id": "folderId",
          "namaFolder": "Folder 1"
        }
      ]
    }
  }
  ```
## Get Subfolder
- Endpoint: `/api/proyek/folder/{folderId}`
- HTTP Method: `GET`
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "data": {
      "folderName": "Folder Name",
      "subFolders": [
        {
          "id": "subFolderId",
          "namaSubFolder": "Nama Sub Folder"
        },
        {
          "id": "subFolderId",
          "namaSubFolder": "Nama Sub Folder"
        },
        {
          "id": "subFolderId",
          "namaSubFolder": "Nama Sub Folder"
        }
      ]
    }
  } 
  ```
  
## Add SubFolder
- Endpoint: `/api/proyek/folder/{folderId}/addSubFolder`
- HTTP Method: `POST`
- Request body
  ```json
  {
    "subFolderName": "Subfolder Name"
  }
  ```
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Subfolder berhasil ditambahkan."
  } 
  ```

## Delete SubFolder
- Endpoint: `/api/proyek/subfolder/{subFolderId}`
- HTTP Method: `DELETE`
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Subfolder berhasil dihapus."
  } 
  ```

## Edit SubFolder
- Endpoint: `/api/proyek/folder/{folderId}/subfolder/{subFolderId}`
- HTTP Method: `PUT`
- Request param: `subFolderName`: string
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Nama subfolder berhasil di edit."
  } 
  ```
  
## Set Proyek on Discussion
- Endpoint: `/api/proyek/{proyekId}/setOnDiscussion`
- HTTP Method: `PUT`
- Request Body:
  ```json
  {
    "partisipan": ["id1", "id2"],
    "accountOfficer": "id1",
    "whatsappGroupLink": "www.link.com"
  }
  ```
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Proyek berhasil diset ke tahap DALAM_DISKUSI."
  } 
  ```
  
## Activate Proyek
- Endpoint: `/api/proyek/{proyekId}/activate`
- HTTP Method: `PUT`
- Request Body:
  Multipart/form data: `files (pdf only)`
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Aktivasi proyek berhasil."
  } 
  ```

## Close Proyek
- Endpoint: `/api/proyek/{proyekId}/close`
- HTTP Method: `PUT`
- Response body (Success):
  ```json
  {
    "code": "200",
    "status": "OK",
    "message": "Proyek proyek diselesaikan."
  } 
  ```
