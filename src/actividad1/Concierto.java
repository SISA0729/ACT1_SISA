package actividad1;

import java.io.Serializable;

public class Concierto implements Serializable {
    private String grupo;
    private String lugar;
    private String fecha;
    private String hora;

    public Concierto(String grupo, String lugar, String fecha, String hora) {
        this.grupo = grupo;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Concierto{" +
                "grupo='" + grupo + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}

