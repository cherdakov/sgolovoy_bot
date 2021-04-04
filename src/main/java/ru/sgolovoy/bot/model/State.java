package ru.sgolovoy.bot.model;

public enum State {
    TRAINING_INFO("TRAINING_INFO"),
    COMMIT_TRAINING("COMMIT_TRAINING"),
    MAIN_MENU("MAIN_MENU"),
    INTRODUCE("INTRODUCE");

    private final String id;

    State(final String id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return id;
    }
}
