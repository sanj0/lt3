package de.sanj0.lt3.engine;

import de.sanj0.lt3.Pieces;

import java.security.SecureRandom;
import java.util.Random;

public class LT3Engine {
    private final Random rng = new SecureRandom();
    private final StrengthControl strengthControl;

    public LT3Engine(final StrengthControl strengthControl) {
        this.strengthControl = strengthControl;
    }

    private MoveRateResult rateMove() {
        return new MoveRateResult(1, Pieces.b(1), null);
    }
}
