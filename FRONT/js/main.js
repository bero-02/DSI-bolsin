// js/main.js

document.addEventListener("DOMContentLoaded", () => {
    // 1. Elementos de la interfaz (según diagrama de clases)
    const btnConsultarUbicacionBolsin = document.getElementById("btnConsultarUbicacionBolsin");
    const btnCancelar = document.getElementById("btnCancelar");
    const pantallaUbicacion = document.getElementById("pantallaUbicacion");
    const mapaGeografico = document.getElementById("mapaGeografico");
    const estadoMapa = document.getElementById("estadoMapa");
    const modalConfirmacion = document.getElementById("modalConfirmacion");
    const btnConfirmarEnvioMailGCM = document.getElementById("btnConfirmarEnvioMailGCM");
    const btnCancelarModal = document.getElementById("btnCancelarModal");
    const lblComisionMedica = document.getElementById("lblComisionMedica");
    
    // Filtros
    const filtroPrecinto = document.getElementById("filtroPrecinto");
    const selectOpcionCM = document.getElementById("selectOpcionCM");

    // Variable de estado para el bolsín seleccionado temporalmente
    let bolsinSeleccionado = null;
    let bolsinesEnviados = [];
    let mapa;
    let capaMarcadores;

    const inicializarMapa = () => {
        if (mapa) return;

        mapa = L.map(mapaGeografico).setView([-38.4161, -63.6167], 4);
        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(mapa);
        capaMarcadores = L.layerGroup().addTo(mapa);
    };

    // 2. Inicialización: mostrarCM() al cargar la pantalla
    const mostrarCM = async () => {
        const cm = await API.obtenerCMUsuarioLogeado();
        lblComisionMedica.textContent = cm;
    };
    mostrarCM().catch(error => {
        lblComisionMedica.textContent = "No disponible";
        console.error("No se pudo mostrar la comisión médica:", error);
    });

    // 3. Flujo Principal: tomarOpConsultarUbicacionBolsin() -> habilitarPantalla()
    btnConsultarUbicacionBolsin.addEventListener("click", async () => {
        btnConsultarUbicacionBolsin.textContent = "Buscando bolsines...";
        btnConsultarUbicacionBolsin.disabled = true;

        try {
            bolsinesEnviados = await API.obtenerDatosLocalizacionBolsines();
            pantallaUbicacion.classList.remove("hidden");
            inicializarMapa();
            mapa.invalidateSize();
            renderizarMapa(bolsinesEnviados);
        } catch (error) {
            console.error("No se pudieron cargar los bolsines:", error);
            alert(`No se pudieron cargar los bolsines. ${error.message}`);
        } finally {
            btnConsultarUbicacionBolsin.textContent = "Actualizar Ubicaciones";
            btnConsultarUbicacionBolsin.disabled = false;
        }
    });

    // 4. Método: mostrarMapaPosicion()
    const renderizarMapa = (bolsines) => {
        inicializarMapa();
        capaMarcadores.clearLayers();
        
        if (bolsines.length === 0) {
            estadoMapa.classList.remove("hidden");
            mapa.setView([-38.4161, -63.6167], 4);
            mapaGeografico.setAttribute("aria-label", "No hay bolsines que coincidan con los filtros");
            return;
        }
        estadoMapa.classList.add("hidden");

        const nombresComisiones = {
            "CM-BA-01": "Buenos Aires",
            "CM-CBA-01": "Córdoba",
            "CM-CBA-02": "Córdoba",
            "CM-JUJ-01": "Jujuy",
            "CM-MDQ-01": "Mar del Plata",
            "CM-MDZ-03": "Mendoza",
            "CM-ROS-02": "Rosario",
            "CM-SLA-01": "Salta"
        };

        const coordenadas = [];
        bolsines.forEach(bolsin => {
            const posicion = [bolsin.latitud, bolsin.longitud];
            const nombreDestino = nombresComisiones[bolsin.cmDestino] ?? bolsin.cmDestino;
            const marcador = L.circleMarker(posicion, {
                radius: 9,
                color: "#ffaa00",
                fillColor: "#00f0ff",
                fillOpacity: 0.9,
                weight: 3
            });
            marcador.bindTooltip(`Precinto ${bolsin.nroPrecinto} · ${nombreDestino}`);
            marcador.on("click", () => tomarSeleccionBolsin(bolsin));
            marcador.addTo(capaMarcadores);
            coordenadas.push(posicion);
        });

        mapaGeografico.setAttribute("aria-label", `Mapa con ${bolsines.length} bolsines`);
        if (coordenadas.length === 1) {
            mapa.setView(coordenadas[0], 8);
        } else {
            mapa.fitBounds(coordenadas, { padding: [35, 35], maxZoom: 8 });
        }
    };

    // 5. Método: tomarSeleccionBolsin() -> pedirConfirmacionEnvioMailGCM()
    const tomarSeleccionBolsin = (bolsin) => {
        bolsinSeleccionado = bolsin;
        // Mostrar confirmación
        modalConfirmacion.classList.remove("hidden");
    };

    // 6. Método: tomarConfirmacionEnvioMailGCM()
    btnConfirmarEnvioMailGCM.addEventListener("click", async () => {
        if (!bolsinSeleccionado) return;
        
        btnConfirmarEnvioMailGCM.textContent = "Enviando...";
        btnConfirmarEnvioMailGCM.disabled = true;

        // Se comunica con el backend que ejecutará el CU 31 (Notificar ubicación de bolsín)
        try {
            await API.enviarEmailGCM(bolsinSeleccionado.nroPrecinto);
            modalConfirmacion.classList.add("hidden");
            bolsinSeleccionado = null;
        } catch (error) {
            console.error("No se pudo enviar el email:", error);
            alert(error.message);
        } finally {
            btnConfirmarEnvioMailGCM.textContent = "Confirmar Envío";
            btnConfirmarEnvioMailGCM.disabled = false;
        }
    });

    // Filtros Locales (Aplicados en el Frontend sobre los datos en memoria)
    const aplicarFiltros = () => {
        const txtPrecinto = filtroPrecinto.value.toLowerCase();
        const txtCM = selectOpcionCM.value;

        const filtrados = bolsinesEnviados.filter(b => {
            const matchPrecinto = b.nroPrecinto.toLowerCase().includes(txtPrecinto);
            const matchCM = txtCM === "" || b.cmDestino === txtCM;
            return matchPrecinto && matchCM;
        });
        
        renderizarMapa(filtrados);
    };

    filtroPrecinto.addEventListener("input", aplicarFiltros);
    selectOpcionCM.addEventListener("change", aplicarFiltros);

    // Cancelar acciones
    btnCancelar.addEventListener("click", () => {
        pantallaUbicacion.classList.add("hidden");
    });
    
    btnCancelarModal.addEventListener("click", () => {
        modalConfirmacion.classList.add("hidden");
        bolsinSeleccionado = null;
    });
});