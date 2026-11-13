const API_BASE = "http://localhost:8080/api/ubicacion-bolsines";

async function apiConsultarUbicacionBolsines() {
    const res = await fetch(`${API_BASE}/consultar`);
    if (!res.ok) throw new Error("Error al consultar seguimiento de bolsines");
    return await res.json();
}

async function apiConfirmarEnvioMailGCM(numeroBolsin) {
    const res = await fetch(`${API_BASE}/notificar-gcm/${numeroBolsin}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" }
    });
    if (!res.ok) throw new Error("Error al enviar notificación al GCM");
    return await res.json();
}