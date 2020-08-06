package model;

public class Specifications {
    private String name, screen, ram, chip, operaSystem;

    public Specifications(String name, String screen, String ram, String chip, String operaSystem) {
        this.name = name;
        this.screen = screen;
        this.ram = ram;
        this.chip = chip;
        this.operaSystem = operaSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getOperaSystem() {
        return operaSystem;
    }

    public void setOperaSystem(String operaSystem) {
        this.operaSystem = operaSystem;
    }

    public void showInfo(){
        System.out.println("********Specifications********");
        System.out.println("SmartPhone name : " + name);
        System.out.println("Screen          : " + screen);
        System.out.println("Ram             : " + ram);
        System.out.println("Chip            : " + chip);
        System.out.println("OperaSystem     : " + operaSystem);
    }
}
