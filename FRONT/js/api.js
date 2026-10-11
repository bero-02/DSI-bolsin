// js/api.js

const API_BASE_URL = "http://localhost:8080";

const API = {
    obtenerCMUsuarioLogeado: async () => "Comisión Médica Central (demo)",

    obtenerDatosLocalizacionBolsines: async () => {
        const response = await fetch(`${API_BASE_URL}/api/mock/bolsines/enviados`);
        if (!response.ok) {
            throw new Error(`El backend respondió con el estado ${response.status}.`);
        }

        const bolsines = await response.json();
        if (!Array.isArray(bolsines)) {
            throw new Error("El backend devolvió un formato de bolsines inesperado.");
        }

        return bolsines.map(bolsin => ({
            nroPrecinto: String(bolsin.numeroDePrecinto),
            cmDestino: bolsin.codigoComisionMedicaDestino,
            latitud: Number(bolsin.latitud),
            longitud: Number(bolsin.longitud),
            numeroBolsin: bolsin.numeroBolsin,
            fechaHoraUltimaActualizacion: bolsin.fechaHoraUltimaActualizacion
        }));
    },

    enviarEmailGCM: async () => {
        throw new Error("El backend todavía no tiene un endpoint para enviar emails.");
    }
};
