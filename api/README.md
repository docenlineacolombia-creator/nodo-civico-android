# API Flask — Nodo Cívico

Servidor de referencia para el proyecto Android.

## Instalación

```bash
cd api
python -m venv venv
source venv/bin/activate   # Windows: venv\Scripts\activate
pip install -r requirements.txt
python app.py
```

El servidor queda disponible en `http://localhost:5000`.

Desde el emulador Android usa `http://10.0.2.2:5000`.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /health | Estado del servidor |
| GET | /categories | Listar categorías |
| GET | /reports | Listar todos los reportes |
| POST | /reports | Crear reporte |
| GET | /reports/{id} | Detalle de reporte |
| PUT | /reports/{id} | Actualizar reporte |
| DELETE | /reports/{id} | Eliminar reporte |
| POST | /users | Identificar o crear usuario |
| GET | /users/{id} | Obtener usuario |

## Notas
- Los datos son en memoria. Al reiniciar el servidor se pierden los cambios.
- Para persistencia real reemplazar las listas por SQLite con `flask-sqlalchemy`.
- No requiere autenticación (simplificado para el proyecto del curso).
