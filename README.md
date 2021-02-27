# EPS-MMO v0.2.770a

This is the second version of my MMO plugin.

## General info

The aim of this plugin is to replicate the way a 'real' MMORPG works.
This includes instances, economy, housing, combat, dynamic events, dungeons, story, etc. <br>
<br>
The goal isn't to create an MMO but to create an 'engine' that creative minds can use to build
their vision without the need of knowledge in programming, giving them the possibility to easily
deliver to their players. <br>
<br>
I want to achieve this by giving creators a webinterface from where they can manage 95% of their game. <br>
<br>
Sadly, this plugin is still months or years away from completion. This v0.2 is not intended for actual use. Most feature aren't implemented, and a lot of configuration is still in the code. 
But it already has way more functionality then the last release. More info on that in the [Update Notes](https://github.com/EliasSchramm/EPS-MMO-Plugin/releases/tag/v0.2.770a)<br>
<br>
Currently, this plugin is written for Spigot/Bungeecord 1.12.2 servers. This is due to the fact that minecraft 1.13+ servers
have gotten way worse when it comes massively multiplayer support. I'll explore the possibility to 
update to higher versions once the core engine is finished.<br>
<br>
You can read about the current functionality in the following section.

## Functionality

Here you can see all functions that are already implemented. `WARNING: This is still a early version. Everything you see can be changed in the future.`

### Characters

Every MMORPG gives the player the possibility to create multiple characters that are independent of another.
So does this plugin. Upon joining the player sees a character selection menu where he can choose from his characters or create new ones.

### Housing 

This is something not every MMORPG has but its something I really enjoy. 
The system is designed in a way that players can rent a house by clicking a shield 
in front of the house/apartment. Once rented the players can use this house as a teleportation point or enter it by
interacting with the doors. But they also need to pay the given prize of their house. At the moment this happens every hour and
also works if the player is offline. 

### NPC

NPC are probably the most important thing in MMORPGs. Currently, they are just static player characters you can interact with.
But I'm planing to make them way more complex and featurerich in the future.
The skin of the NPC an be changed via the web-interface.

### Static Effects

You want to decorate your world with watersprinkles or you want to draw attention to an important point? Static effects are the thing for you! Once placed, they can be modified in many ways via the web-interface. 

### Ranks

Ranks are used to give certain players special permissions like building or changing NPC. 
Every rank has its own prefix that is always shown in the tablist, and the Chat.

### Misc

`/gmc, /gms, /gmspec` for easy gamemode switching.
<br>
`/mutechat` gives the server staff the possibility to mut the chat on a global scale.

