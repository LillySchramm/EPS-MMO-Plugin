import { Item } from "./itemType"
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

export type getAllItemsResponse = {
    items:Item[]
}

export type getResourcepackVersionResponse = {
    ver: number,
    last_changed: string
}

export type itemUpdateRequest = {
    base: string,
    name: string,
    data: string
}