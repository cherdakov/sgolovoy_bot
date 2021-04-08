package ru.sgolovoy.bot.model;

import java.util.List;

public class Command {

    public static final String START = "/start";
    public static final String CHANGE_NAME = "/change_name";
    public static final String NAME = "/name";
    public static final String EDIT_TRAINING = "/edit_training";
    public static final String CREATE_TRAINING = "/create_training";

    public static final List<String> COMMANDS = List.of(CHANGE_NAME, NAME, CREATE_TRAINING, EDIT_TRAINING);
}
