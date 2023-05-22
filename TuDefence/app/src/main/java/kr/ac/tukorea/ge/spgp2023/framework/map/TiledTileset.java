package kr.ac.tukorea.ge.spgp2023.framework.map;

public class TiledTileset {
    private final TiledMap map;
    //////////////////////////////////////////////////
    // from tmj json
    public int columns, tilecount;
    public int tilewidth, tileheight;
    public int imagewidth, imageheight;
    public int margin, spacing;
    public String image;

    public TiledTileset(TiledMap map) {
        this.map = map;
    }
    //////////////////////////////////////////////////
}
