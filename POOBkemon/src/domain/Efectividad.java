package domain;

public class Efectividad {

    protected static final double superefectivo = 2.0;
    protected static final double efectivo = 0.5;
    protected static final double inefectivo = 0.0;
    protected static final double neutral = 1.0;

    public static double calcular(String tipoAtaque, String tipoDefensa) {
        switch (tipoAtaque.toLowerCase()) {
            case "acero": return efectividadAcero(tipoDefensa);
            case "agua": return efectividadAgua(tipoDefensa);
            case "bicho": return efectividadBicho(tipoDefensa);
            case "dragon": return efectividadDragon(tipoDefensa);
            case "electrico": return efectividadElectrico(tipoDefensa);
            case "fantasma": return efectividadFantasma(tipoDefensa);
            case "fuego": return efectividadFuego(tipoDefensa);
            case "hada": return efectividadHada(tipoDefensa);
            case "hielo": return efectividadHielo(tipoDefensa);
            case "lucha": return efectividadLucha(tipoDefensa);
            case "normal": return efectividadNormal(tipoDefensa);
            case "planta": return efectividadPlanta(tipoDefensa);
            case "psiquico": return efectividadPsiquico(tipoDefensa);
            case "roca": return efectividadRoca(tipoDefensa);
            case "siniestro": return efectividadSiniestro(tipoDefensa);
            case "tierra": return efectividadTierra(tipoDefensa);
            case "veneno": return efectividadVeneno(tipoDefensa);
            case "volador": return efectividadVolador(tipoDefensa);
            default: return neutral;

        }
    }

    private static double efectividadAcero(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "hada": case "hielo": case "roca": return superefectivo;
            case "acero":case "agua": case "electrico": case "fuego": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadAgua(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "fuego": case "roca": case "tierra": return superefectivo;
            case "agua": case "dragon": case "planta": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadBicho(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "planta": case "psiquico": case "siniestro": return superefectivo;
            case "acero": case "fantasma": case "fuego": case "hada": case "lucha": case "veneno": case "volador":return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadDragon(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "dragon": return superefectivo;
            case "acero": return efectivo;
            case "hada": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadElectrico(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "agua": case "volador": return superefectivo;
            case "dragon": case "electrico": case "planta": return efectivo;
            case "tierra": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadFantasma(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "fantasma": case "psiquico": return superefectivo;
            case "siniestro": return efectivo;
            case "normal": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadFuego(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "acero": case "bicho": case "hielo": case "planta": return superefectivo;
            case "agua": case "dragon": case "fuego": case "roca": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadHada(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "dragon": case "lucha": case "siniestro": return superefectivo;
            case "acero": case "fuego": case "veneno": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadHielo(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "dragon": case "planta": case "tierra": case "volador": return superefectivo;
            case "acero": case "agua": case "fuego": case "hielo": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadLucha(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "acero": case "hielo": case "normal": case "roca": case "siniestro": return superefectivo;
            case "bicho": case "hada": case "psiquico": case "veneno": case "volador": return efectivo;
            case "fantasma": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadNormal(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "acero": case "roca": return efectivo;
            case "fantasma": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadPlanta(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "agua": case "tierra": case "roca": return superefectivo;
            case "acero": case "bicho": case "dragon": case "fuego": case "planta": case "veneno": case "volador": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadPsiquico(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "lucha": case "veneno": return superefectivo;
            case "acero": case "psiquico": return efectivo;
            case "siniestro": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadRoca(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "bicho": case "fuego": case "hielo": case "volador": return superefectivo;
            case "acero": case "lucha": case "tierra": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadSiniestro(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "fantasma": case "psiquico": return superefectivo;
            case "hada": case "lucha": case "siniestro": return efectivo;
            default: return neutral;
        }
    }

    private static double efectividadTierra(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "acero": case "electrico": case "fuego": case "roca": case "veneno": return superefectivo;
            case "bicho": case "planta": return efectivo;
            case "volador": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadVeneno(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "hada": case "planta": return superefectivo;
            case "fantasma": case "roca": case "tierra": case "veneno":return efectivo;
            case "acero": return inefectivo;
            default: return neutral;
        }
    }

    private static double efectividadVolador(String tipoDefensa) {
        switch (tipoDefensa.toLowerCase()) {
            case "bicho": case "lucha": case "planta": return superefectivo;
            case "acero": case "electrico": case "roca": return efectivo;
            default: return neutral;
        }
    }
}

 
