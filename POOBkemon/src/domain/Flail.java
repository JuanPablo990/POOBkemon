package domain;

public class Flail extends Movimiento {
    public Flail() {
        super("Flail", "Normal", 55, 100, 15, 0);
    }

    @Override
    protected int calcularDanio(Pokemon usuario, Pokemon objetivo, double efectividad) {
        // Calculamos el porcentaje de PS actuales respecto a los máximos
        double porcentajePS = (double)usuario.getPs() / usuario.getPsMaximos();
        
        // Ajustamos la potencia según los rangos establecidos
        if (porcentajePS <= 0.0417) {        // Menos del 4.17% PS
            this.potencia = 200;
        } else if (porcentajePS <= 0.1042) { // 4.17%-10.42%
            this.potencia = 150;
        } else if (porcentajePS <= 0.2083) { // 10.42%-20.83%
            this.potencia = 100;
        } else if (porcentajePS <= 0.3542) { // 20.83%-35.42%
            this.potencia = 80;
        } else if (porcentajePS <= 0.6875) { // 35.42%-68.75%
            this.potencia = 40;
        } else {                            // Más del 68.75%
            this.potencia = 20;
        }
        
        // Mostramos información de debug (opcional)
        System.out.println(usuario.getNombre() + " usa Flail con " + 
                          String.format("%.2f", porcentajePS*100) + "% PS (" + 
                          this.potencia + " de potencia)");
        
        // Calculamos el daño con la potencia ajustada
        return super.calcularDanio(usuario, objetivo, efectividad);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // No tiene efecto secundario adicional
    }
}