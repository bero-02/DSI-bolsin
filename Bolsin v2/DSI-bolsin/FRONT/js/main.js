let mapa = null;
let capaMarcadores = null;
let bolsinesCargados = [];
let bolsinSeleccionadoActual = null;

document.addEventListener("DOMContentLoaded", () => {
    // PASO 1 DEL CU 36: El Encargado de Bolsines selecciona la opción para consultar seguimiento
    document.getElementById("btnConsultarUbicacionBolsin")
        .addEventListener("click", tomarOpConsultarUbicacionBolsin);

    // Eventos de filtros y botones de la PantallaUbicacionBolsines
    document.getElementById("txtFiltroPrecinto").addEventListener("input", aplicarFiltros);
    document.getElementById("selectOptionCM").addEventListener("change", aplicarFiltros);

    document.getElementById("btnCancelar").addEventListener("click", cancelarCU);

    document.getElementById("btnCancelarEnvio").addEventListener("click", () => {
        document.getElementById("modalConfirmacion").classList.add("hidden");
        bolsinSeleccionadoActual = null;
    });

    document.getElementById("btnConfirmarEnvioMailGCM")
        .addEventListener("click", tomarConfirmacionEnvioMailGCM);

    document.getElementById("btnCerrarExito").addEventListener("click", () => {
        document.getElementById("modalExitoMail").classList.add("hidden");
    });
});

/**
 * Mensaje 1 y 2 del Diagrama de Secuencia:
 * EncargadoDeBolsines -> PantallaUbicacionBolsines : tomarOpConsultarUbicacionBolsin()
 * PantallaUbicacionBolsines -> PantallaUbicacionBolsines : habilitarPantalla()
 * PantallaUbicacionBolsines -> GestorUbicacionBolsines : tomarOpConsultarUbicacion()
 */
async function tomarOpConsultarUbicacionBolsin() {
    const btnConsultar = document.getElementById("btnConsultarUbicacionBolsin");
    btnConsultar.textContent = "Buscando bolsines...";
    btnConsultar.disabled = true;

    try {
        // 1. Auto-mensaje habilitarPantalla() según diagrama de secuencia
        habilitarPantalla();

        // 2. Mensaje al GestorUbicacionBolsines: tomarOpConsultarUbicacion()
        const data = await apiConsultarUbicacionBolsines();

        // 3. Retorno del Gestor -> PantallaUbicacionBolsines : mostrarCM()
        mostrarCM(data.comisionMedicaUsuario, data.usuarioLogueado);

        // 4. Retorno del Gestor -> PantallaUbicacionBolsines : mostrarMapaPosicion()
        bolsinesCargados = data.bolsinesEnviados || [];
        cargarOpcionesCMDestino(bolsinesCargados);
        mostrarMapaPosicion(bolsinesCargados);
    } catch (err) {
        alert("No se pudo conectar con el Backend: " + err.message);
    } finally {
        btnConsultar.textContent = "Actualizar Ubicaciones GPS";
        btnConsultar.disabled = false;
    }
}

/**
 * Método de PantallaUbicacionBolsines: habilitarPantalla()
 */
function habilitarPantalla() {
    const pantallaUbicacion = document.getElementById("pantallaUbicacion");
    const btnCancelar = document.getElementById("btnCancelar");

    pantallaUbicacion.classList.remove("hidden");
    btnCancelar.classList.remove("hidden");

    if (!mapa) {
        mapa = L.map("mapaGeografico").setView([-32.8, -62.2], 6);
        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(mapa);
        capaMarcadores = L.layerGroup().addTo(mapa);
    }

    // Ajusta el tamaño del contenedor luego de quitar la clase .hidden
    setTimeout(() => mapa.invalidateSize(), 100);
}

/**
 * Método de PantallaUbicacionBolsines: mostrarCM()
 */
function mostrarCM(nombreCM, usuario) {
    document.getElementById("lblCMOrigen").textContent = nombreCM;
    document.getElementById("lblUsuario").textContent = usuario;
}

function cargarOpcionesCMDestino(bolsines) {
    const select = document.getElementById("selectOptionCM");
    const destinosUnicos = [...new Set(bolsines.map(b => b.comisionMedicaDestino))];
    select.innerHTML = '<option value="">Todas las Comisiones Médicas Destino</option>';
    destinosUnicos.forEach(cm => {
        const opt = document.createElement("option");
        opt.value = cm;
        opt.textContent = cm;
        select.appendChild(opt);
    });
}

function aplicarFiltros() {
    const textoPrecinto = document.getElementById("txtFiltroPrecinto").value.trim().toLowerCase();
    const cmDestino = document.getElementById("selectOptionCM").value;

    const filtrados = bolsinesCargados.filter(b => {
        const coincidePrecinto = b.numeroPrecinto.toLowerCase().includes(textoPrecinto);
        const coincideCM = cmDestino === "" || b.comisionMedicaDestino === cmDestino;
        return coincidePrecinto && coincideCM;
    });

    mostrarMapaPosicion(filtrados);
}

/**
 * Método de PantallaUbicacionBolsines: mostrarMapaPosicion()
 */
function mostrarMapaPosicion(bolsines) {
    capaMarcadores.clearLayers();
    const contenedorLista = document.getElementById("listaBolsines");
    const estadoMapa = document.getElementById("estadoMapa");
    contenedorLista.innerHTML = "";

    if (bolsines.length === 0) {
        estadoMapa.classList.remove("hidden");
        contenedorLista.innerHTML = `<p class="hint">No se encontraron bolsines con esos criterios (Flujo Alternativo A1/A2).</p>`;
        return;
    }

    estadoMapa.classList.add("hidden");
    const coordenadas = [];

    bolsines.forEach(b => {
        const posicion = [b.latitud, b.longitud];
        coordenadas.push(posicion);

        // Marcador estilo neón cian/naranja en el mapa
        const marker = L.circleMarker(posicion, {
            radius: 10,
            color: "#ffaa00",
            fillColor: "#00f0ff",
            fillOpacity: 0.9,
            weight: 3
        }).addTo(capaMarcadores);

        marker.bindPopup(`
            <div style="color:#0f172a; min-width: 180px;">
                <strong>Bolsín #${b.numeroBolsin}</strong><br>
                Precinto: <strong>${b.numeroPrecinto}</strong><br>
                Destino: ${b.comisionMedicaDestino}<br>
                Últ. reporte: ${b.ultimaActualizacion}<br><br>
                <button onclick="tomarSeleccionBolsin(${b.numeroBolsin})"
                        style="background:#0a0f18;color:#00f0ff;border:1px solid #00f0ff;padding:6px 10px;border-radius:4px;cursor:pointer;width:100%;font-weight:600;">
                    Notificar al GCM
                </button>
            </div>
        `);

        // Tarjeta en el listado derecho
        const card = document.createElement("div");
        card.className = "bolsin-card";
        card.innerHTML = `
            <h4>📦 Bolsín #${b.numeroBolsin} — Precinto: ${b.numeroPrecinto}</h4>
            <p><strong>CM Destino:</strong> ${b.comisionMedicaDestino}</p>
            <p><strong>Coordenadas:</strong> ${b.latitud}, ${b.longitud}</p>
            <p><strong>Última act. GPS:</strong> ${b.ultimaActualizacion}</p>
        `;
        card.addEventListener("click", () => {
            mapa.setView(posicion, 8);
            marker.openPopup();
            tomarSeleccionBolsin(b.numeroBolsin);
        });
        contenedorLista.appendChild(card);
    });

    if (coordenadas.length === 1) {
        mapa.setView(coordenadas[0], 8);
    } else if (coordenadas.length > 1) {
        mapa.fitBounds(coordenadas, { padding: [40, 40], maxZoom: 8 });
    }
}

/**
 * Paso 7 y 8 del Diagrama de Secuencia:
 * EncargadoDeBolsines -> PantallaUbicacionBolsines : tomarSeleccionBolsin()
 * GestorUbicacionBolsines -> PantallaUbicacionBolsines : pedirConfirmacionEnvioMailGCM()
 */
window.tomarSeleccionBolsin = function(numeroBolsin) {
    bolsinSeleccionadoActual = bolsinesCargados.find(b => b.numeroBolsin === numeroBolsin);
    if (!bolsinSeleccionadoActual) return;
    pedirConfirmacionEnvioMailGCM(bolsinSeleccionadoActual);
};

function pedirConfirmacionEnvioMailGCM(bolsin) {
    document.getElementById("textoConfirmacion").innerHTML = `
        ¿Desea enviar un correo electrónico al <strong>Gerente de Comisión Médica (GCM)</strong> de 
        <strong style="color:#00f0ff;">${bolsin.comisionMedicaDestino}</strong> informando la ubicación actual del 
        <strong>Bolsín #${bolsin.numeroBolsin}</strong> (Precinto ${bolsin.numeroPrecinto})?
    `;
    document.getElementById("modalConfirmacion").classList.remove("hidden");
}

/**
 * Paso 9, 10 y 11 del Diagrama de Secuencia:
 * EncargadoDeBolsines -> PantallaUbicacionBolsines : tomarConfirmacionEnvioMailGCM()
 * PantallaUbicacionBolsines -> GestorUbicacionBolsines : tomarConfirmacionEnvioMailGCM()
 * Incluye CU 31 (GestorNotificarUbicacionBolsin) y muestra ventana de correo enviado.
 */
async function tomarConfirmacionEnvioMailGCM() {
    if (!bolsinSeleccionadoActual) return;
    const btnConfirmar = document.getElementById("btnConfirmarEnvioMailGCM");
    btnConfirmar.textContent = "Enviando...";
    btnConfirmar.disabled = true;

    try {
        const res = await apiConfirmarEnvioMailGCM(bolsinSeleccionadoActual.numeroBolsin);
        document.getElementById("modalConfirmacion").classList.add("hidden");

        document.getElementById("mailPara").textContent = res.emailGCM;
        document.getElementById("mailCM").textContent = res.comisionMedicaDestino;
        document.getElementById("mailFecha").textContent = res.fechaHoraEnvio;
        document.getElementById("mailAsunto").textContent = res.asunto;
        document.getElementById("mailCuerpo").textContent = res.cuerpoMensaje;

        document.getElementById("modalExitoMail").classList.remove("hidden");
    } catch (err) {
        alert("Error al enviar el correo: " + err.message);
    } finally {
        btnConfirmar.textContent = "Confirmar Envío";
        btnConfirmar.disabled = false;
    }
}

/**
 * Flujo Alternativo A4: El EB cancela la ejecución del caso de uso.
 */
function cancelarCU() {
    document.getElementById("pantallaUbicacion").classList.add("hidden");
    document.getElementById("btnCancelar").classList.add("hidden");
    document.getElementById("btnConsultarUbicacionBolsin").textContent = "Consultar Seguimiento de Bolsines";
    document.getElementById("lblCMOrigen").textContent = "Sin consultar";
    document.getElementById("lblUsuario").textContent = "Sin consultar";
}