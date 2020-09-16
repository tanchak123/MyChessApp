package sounds;

public enum Sounds {
    CONGRATS("/sounds/congrats.wav"),
    STEP("/sounds/step.wav"),
    DRAW("/sounds/draw.wav"),
    VICTORY("/sounds/victory.wav"),
    PICKUP("/sounds/pickup.wav"),
    WARNING("/sounds/warning.wav"),
    CHOOSE("/sounds/choose.wav"),
    HIT1("/sounds/hit1.wav"),
    HIT2("/sounds/hit2.wav"),
    HIT3("/sounds/hit3.wav");

    private final String url;
    Sounds(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
