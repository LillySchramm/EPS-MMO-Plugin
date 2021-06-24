import { NPC } from "./npcType"
import { StaticEffect } from "./staticEffectType"

export type loginResponse = {
    successfull: boolean,
    session: string;
}

export type verifyResponse = {
    verified: boolean
}

export type getAllNPCResponse = {
    npc:NPC[]
}

export type getAllStaticEffectsResponse = {
    npc:StaticEffect[]
}