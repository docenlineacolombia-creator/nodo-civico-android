# Nodo Cívico — Android App

> Aplicación móvil Android para reportes comunitarios, seguimiento de incidencias y sincronización local/remota.

[![Android](https://img.shields.io/badge/Android-Java-green)]()
[![minSdk](https://img.shields.io/badge/minSdk-26-blue)]()
[![Room](https://img.shields.io/badge/Room-2.6.1-orange)]()
[![Retrofit](https://img.shields.io/badge/Retrofit-2.9.0-red)]()

---

## Stack tecnológico

| Capa | Tecnología |
|------|------------|
| UI | Android Views + ViewBinding + Material 3 |
| Navegación | Navigation Component (Fragments) |
| Persistencia local | Room (SQLite) |
| API REST | Retrofit 2 + Gson |
| Arquitectura | MVVM — ViewModel + LiveData |
| Alarmas | AlarmManager (exactas) |
| Eventos sistema | 3 BroadcastReceivers |
| Conectividad | NetworkMonitor (LiveData en tiempo real) |

---

## Estructura de paquetes

```
app/src/main/java/com/nodocivico/app/
├── data/
│   ├── local/
│   │   ├── dao/          ReportDao, CategoryDao, ReminderDao, FollowUpDao, SyncEventDao, UserDao
│   │   └── AppDatabase   Room singleton con seed automático de categorías y usuario
│   ├── model/            User, Report, Category, Reminder, SyncEvent, FollowUp
│   ├── remote/
│   │   ├── ApiService    Contratos Retrofit (GET/POST/PUT reports y categories)
│   │   └── ApiClient     Singleton Retrofit → http://10.0.2.2:5000/
│   ├── repository/       ReportRepository, CategoryRepository, ReminderRepository,
│   │                     FollowUpRepository, SyncEventRepository, UserRepository
│   └── sync/
│       └── SyncManager   Envía reportes pendientes a la API y marca syncronizados en Room
├── receiver/
│   ├── BootReceiver          Reprograma alarmas tras reinicio
│   ├── ConnectivityReceiver  Auto-sync al recuperar conexión
│   └── ReminderReceiver      Notificación local con canal propio
├── ui/
│   ├── MainActivity          BottomNav + NetworkMonitor banner
│   ├── home/                 HomeFragment
│   ├── report/               ReportListFragment, ReportDetailFragment, CreateReportFragment,
│   │                         EditReportFragment, ReportViewModel, ReportAdapter
│   ├── reminder/             RemindersFragment, ReminderViewModel
│   ├── sync/                 SyncStatusFragment, SyncViewModel
│   ├── settings/             SettingsFragment
│   ├── profile/              ProfileFragment, ProfileViewModel
│   └── category/             CategoryViewModel
└── util/
    ├── AlarmScheduler        Programa/cancela alarmas exactas
    └── NetworkMonitor        LiveData<Boolean> de conectividad

api/
├── app.py                Servidor Flask de referencia
├── requirements.txt
└── README.md
```

---

## Cómo ejecutar

### App Android
1. Abrir en Android Studio.
2. Seleccionar un emulador (API 26+) o dispositivo físico.
3. Ejecutar `Run > app`.

### Servidor Flask
```bash
cd api
python -m venv venv && source venv/bin/activate
pip install -r requirements.txt
python app.py
```
El servidor arranca en `http://localhost:5000`. El emulador lo alcanza en `http://10.0.2.2:5000`.

---

## Endpoints API

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /health | Estado del servidor |
| GET | /categories | Listar categorías |
| GET | /reports | Listar todos los reportes |
| POST | /reports | Crear reporte |
| GET | /reports/{id} | Detalle |
| PUT | /reports/{id} | Actualizar |
| DELETE | /reports/{id} | Eliminar |
| POST | /users | Identificar / crear usuario |
| GET | /users/{id} | Obtener usuario |

---

## Flujo end-to-end

```
Usuario crea reporte → Room local (pendingSync=true)
    ↓ ConnectivityReceiver detecta red
    ↓ SyncManager itera pendientes
    ↓ Retrofit POST /reports
    ↓ Room.markSynced(id, remoteId)
    ↓ SyncEvent guardado con resultado
```

---

## Entregables

| # | Contenido | % | Estado |
|---|-----------|---|--------|
| 1 | Base, navegación, vistas, dominio | 20% | ✅ |
| 2 | Room CRUD, offline, sincronización, alarmas | 35% | ✅ |
| 3 | Integración completa, BottomNav, NetworkMonitor, Flask | 45% | ✅ |

---

## Colores del diseño

| Token | Hex |
|-------|-----|
| primary | `#2563EB` |
| primary-2 | `#0F4BD8` |
| success | `#16A34A` |
| warning | `#F59E0B` |
| danger | `#DC2626` |
| bg | `#EEF3FB` |
