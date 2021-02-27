const particleTypes = require("./particleNameDir")

const particleTypeDefaults = {
    "single"            :["single", "REDSTONE", "1", "1", "1"], // TYPE EFFECT R G B
    "circle"            :["circle", "REDSTONE", "1", "20","1", "1", "1"], // TYPE EFFECT RADIUS RING_POINTS R G B
    "pillar"            :["pillar", "REDSTONE", "1", "10", "2", "10", "1", "1", "1"] // TYPE EFFECT RADIUS RING_POINTS HEIGHT RINGS R G B
}

module.exports = particleTypeDefaults;