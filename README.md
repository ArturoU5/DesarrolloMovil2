# RefreshPro Clean – App móvil Android (Kotlin)

Aplicación nativa Android para una lavandería a domicilio: permite registrar pedidos,
listarlos, ver su detalle y ubicar los puntos de recojo en Google Maps.

Curso: Desarrollo de Aplicaciones Móviles 2 – IDAT (Evaluación parcial)

## Integrantes
| Integrante | Rol / aporte |
|---|---|
| Integrante 1 | (completar) |
| Integrante 2 | (completar) |
| Integrante 3 | (completar) |

## Funcionalidades
- Splash, Login, **registro de clientes** (con validaciones visuales) y Home con resumen de pedidos.
- **Catálogo de servicios** (RecyclerView) y detalle de cada servicio, con botón para solicitarlo.
- Listado de pedidos con **RecyclerView + CardView**.
- Registro de pedidos con **TextInputLayout**, selector de servicio, calendario y ubicación.
- Detalle del pedido y cambio de estado (PENDIENTE → CONFIRMADO → FINALIZADO).
- **Google Maps**: punto de recojo de un pedido o todos los pedidos, con distancia al usuario.
- **Permisos de ubicación** en tiempo de ejecución, ubicación actual o simulada (Plaza de Armas de Tumbes).
- Material Design 3: Toolbar, Buttons, FAB, Material Icons, paleta de colores propia.
- **Navigation Drawer con Fragments** (Inicio, Mis pedidos, Servicios, Contáctanos, Nosotros) y Activities para detalle, formulario y mapa.
- Envío de datos con `Intent extras` y `arguments` (Activity → Fragment); ciclo de vida registrado en Logcat.

## Arquitectura
```
com.refreshpro.lavanderia
├── domain/model      -> Pedido, EstadoPedido, Servicio, Usuario
├── data/repository   -> PedidoRepository, AuthRepository, ServiciosCatalogo (datos en memoria)
├── ui                -> BaseActivity (ciclo de vida) y una carpeta por pantalla
│   ├── home          -> HomeActivity (Navigation Drawer) y HomeFragment
│   ├── contacto      -> ContactosFragment, NosotrosFragment
│   ├── splash, login (Login y Registro), home
│   ├── servicios     -> ServiciosFragment, ServicioAdapter, ServicioDetalleActivity
│   ├── pedidos       -> PedidosFragment, PedidoAdapter, NuevoPedidoActivity
│   ├── detalle       -> DetalleActivity
│   └── mapa          -> MapaActivity
└── utils             -> Validaciones, UbicacionHelper, Extras, EstadoUi
```

## Requisitos
- Android Studio (Koala o superior) y JDK 17
- Android SDK 34
- Emulador con **Google APIs / Google Play** (API 30+) o dispositivo físico
- Una **API Key de Google Maps SDK for Android** (Google Cloud Console)

## Instalación
1. Clonar el repositorio:
   `git clone <URL-DEL-REPOSITORIO>`
2. Abrir la carpeta con Android Studio y esperar a que termine **Gradle Sync**.
3. En la raíz del proyecto, crear o editar el archivo `local.properties` y agregar:
   ```
   MAPS_API_KEY=TU_API_KEY_AQUI
   ```
   (Este archivo no se sube a GitHub.)

## Ejecución
1. Seleccionar un emulador o conectar un dispositivo con depuración USB.
2. Pulsar **Run ▶**.
3. Para ver el ciclo de vida: Logcat → filtrar por `CicloVida`.

## Flujo de prueba
1. Esperar el **Splash** (2 s) → pasa al **Login**.
2. Probar el login vacío y con un correo inválido (se ven los errores).
3. Pulsar **Regístrate**, crear una cuenta (probar contraseñas distintas) y volver al login.
4. Ingresar con `admin@refreshpro.com` / `123456` o con la cuenta creada.
4. En **Inicio**, revisar el resumen. Abrir el menú lateral (☰) y recorrer **Mis pedidos, Servicios, Contáctanos y Nosotros**.
5. Tocar un pedido → **Detalle** → pulsar **Marcar como Confirmado** (cambia el estado).
6. Pulsar **Ver en el mapa**: aceptar el permiso de ubicación y ver el marcador y la distancia.
7. Volver a **Pedidos** → botón **+** → llenar el formulario (probar validaciones),
   pulsar **Usar mi ubicación** y **Guardar pedido**. El nuevo pedido aparece en la lista.
8. En **Home** pulsar **Mapa de recojos** para ver todos los puntos.
9. Denegar el permiso de ubicación y comprobar que se usa la ubicación simulada.
10. Cerrar sesión con el ícono de la barra superior.

> En el emulador, la ubicación se puede cambiar en *Extended controls (⋮) → Location*.

## Datos de acceso de prueba
| Correo | Contraseña |
|---|---|
| admin@refreshpro.com | 123456 |
| cliente@refreshpro.com | 123456 |
