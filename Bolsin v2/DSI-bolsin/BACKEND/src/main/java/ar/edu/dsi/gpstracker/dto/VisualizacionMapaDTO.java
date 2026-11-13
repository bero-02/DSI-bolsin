package ar.edu.dsi.gpstracker.dto;

import java.util.List;

public class VisualizacionMapaDTO {
    private String comisionMedicaUsuario;
    private String usuarioLogueado;
    private List<BolsinUbicacionDTO> bolsinesEnviados;

    public VisualizacionMapaDTO() {}

    public VisualizacionMapaDTO(String comisionMedicaUsuario, String usuarioLogueado, List<BolsinUbicacionDTO> bolsinesEnviados) {
        this.comisionMedicaUsuario = comisionMedicaUsuario;
        this.usuarioLogueado = usuarioLogueado;
        this.bolsinesEnviados = bolsinesEnviados;
    }

    public String getComisionMedicaUsuario() { return comisionMedicaUsuario; }
    public void setComisionMedicaUsuario(String comisionMedicaUsuario) { this.comisionMedicaUsuario = comisionMedicaUsuario; }
    public String getUsuarioLogueado() { return usuarioLogueado; }
    public void setUsuarioLogueado(String usuarioLogueado) { this.usuarioLogueado = usuarioLogueado; }
    public List<BolsinUbicacionDTO> getBolsinesEnviados() { return bolsinesEnviados; }
    public void setBolsinesEnviados(List<BolsinUbicacionDTO> bolsinesEnviados) { this.bolsinesEnviados = bolsinesEnviados; }
}