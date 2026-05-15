# Nodo Cívico — Android App

Aplicación móvil Android para reportes comunitarios, seguimiento de incidencias y sincronización local/remota.

## Stack
- **Android** (Java, minSdk 26, targetSdk 34)
- **Room** — persistencia local SQLite
- **Retrofit 2 + Gson** — consumo de API REST
- **Navigation Component** — navegación por fragments
- **LiveData + ViewModel** — arquitectura reactiva
- **AlarmManager** — recordatorios exactos
- **BroadcastReceiver** — BootReceiver, ConnectivityReceiver, ReminderReceiver
- **Material Components** — UI consistente con el prototipo visual

## Estructura
```
app/src/main/
├── java/com/nodocivico/app/
│   ├── data/
│   │   ├── local/         ← AppDatabase, DAOs
│   │   ├── model/         ← User, Report, Category, Reminder, SyncEvent, FollowUp
│   │   ├── remote/        ← ApiService, ApiClient (Retrofit)
│   │   ├── repository/    ← ReportRepository, CategoryRepository, ReminderRepository...
│   │   └── sync/          ← SyncManager
│   ├── receiver/          ← BootReceiver, ConnectivityReceiver, ReminderReceiver
│   ├── ui/
│   │   ├── home/          ← HomeFragment
│   │   ├── report/        ← ReportListFragment, DetailFragment, Create, Edit + Adapter
│   │   ├── reminder/      ← RemindersFragment, ReminderViewModel
│   │   ├── sync/          ← SyncStatusFragment, SyncViewModel
│   │   ├── settings/      ← SettingsFragment
│   │   ├── profile/       ← ProfileFragment
│   │   └── category/      ← CategoryViewModel
│   └── util/              ← AlarmScheduler
└── res/
    ├── layout/            ← todos los fragments + items
    ├── drawable/          ← 17 backgrounds y chips del diseño
    ├── navigation/        ← nav_graph.xml
    └── values/            ← colors, strings, themes, styles
```

## API
La app apunta por defecto a `http://10.0.2.2:5000/` (emulador → localhost Flask).
Cambia `BASE_URL` en `ApiClient.java` según el servidor del equipo.

### Endpoints esperados
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /reports | Listar reportes |
| POST | /reports | Crear reporte |
| PUT | /reports/{id} | Actualizar reporte |
| GET | /reports/{id} | Detalle |
| GET | /categories | Listar categorías |

## Entregables
- **Entregable 1 (20%)** — Estructura base, navegación, vistas ✅
- **Entregable 2 (35%)** — Room CRUD, sincronización, recordatorios ✅
- **Entregable 3 (45%)** — Integración completa, pruebas, sustentación
