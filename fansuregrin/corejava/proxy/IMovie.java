package fansuregrin.corejava.proxy;

public interface IMovie {
    void play();
}

class MyNeighborTotoro implements IMovie {
    String name = "My Neighbor Totoro";

    @Override
    public void play() {
        System.out.println("playing <" + name + ">");
    }
}

class ScreenRoom implements IMovie {
    IMovie movie;

    public ScreenRoom(IMovie movie) {
        this.movie = movie;
    }

    @Override
    public void play() {
        System.out.println("playing Ads");
        movie.play();
        System.out.println("playing Ads");
    }
}