package de.epsdev.plugins.MMO.combat;

public class AttackCollection {
    public Attack[] attacks = new Attack[4];
    public Attack[] support = new Attack[4];

    public AttackCollection(Attack[] attacks, Attack[] support) {
        this.attacks = attacks;
        this.support = support;
    }
}
