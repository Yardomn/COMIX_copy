package core.comix.controller;

import core.comix.view.UI;

public abstract class Command {
    protected UI ui;

    public Command (UI ui){
        this.ui = ui;
    }
}
