package figures;

public enum Figures {
    PAWNB("/imgs/PAWB.png"),
    PAWNW("/imgs/PAWNW.png"),
    KNIGHTB("/imgs/KNIGHTB.png"),
    KNIGHTW("/imgs/KNIGHTW.png"),
    KINGB("/imgs/KINGB.png"),
    KINGW("/imgs/KINGW.png"),
    CASTLEB("/imgs/CASTLEB.png"),
    CASTLEW("/imgs/CASTLEW.png"),
    OFFICERB("/imgs/OFFICERB.png"),
    OFFICERW("/imgs/OFFICERW.png"),
    QUEENB("/imgs/QUEENB.png"),
    QUEENW("/imgs/QUEENW.png"),
    SPACE(" "),
    VICTORYW("/imgs/WhiteVictory.png"),
    VICTORYLB("/imgs/sadPawnLB.png"),
    VICTORYRB("/imgs/sadPawnRB.png"),
    VICTORYLW("/imgs/afraidQueenLW.png"),
    VICTORYRW("/imgs/afraidQueenRW.png"),
    VICTORYB("/imgs/black king win.png");

    private String url;
    Figures(String i) {
        url = i;
    }

    public String getUrl() {
        return url;
    }
}
