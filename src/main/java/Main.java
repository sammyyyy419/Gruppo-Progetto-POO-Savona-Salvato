public class Main {
    public static void main(String[] args) {
        // Il vero punto di partenza del tuo Enterprise Cinema
        controller.Controller ctrl = new controller.Controller();
        new gui.Home(ctrl);
    }
}