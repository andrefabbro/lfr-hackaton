package com.hackaton.liferay.content.targeting.rule.persona;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André Fabbro
 */
public class PersonaResolver {

    private Map<String, Persona> personas;

    private static PersonaResolver INSTANCE = null;

    private PersonaResolver() {

        personas = new HashMap<String, Persona>();

        personas.put(Persona.TRAVEL, new Travel());
        personas.put(Persona.GOURMET, new Gourmet());
    }

    public static synchronized PersonaResolver getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new PersonaResolver();
        }

        return INSTANCE;
    }

    public Persona getPersona(String label) {

        return personas.get(label);
    }

}
