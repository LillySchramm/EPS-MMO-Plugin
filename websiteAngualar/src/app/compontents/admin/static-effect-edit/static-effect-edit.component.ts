import { Component, OnInit, Input } from '@angular/core';
import { APIService } from 'src/app/services/api.service';
import { StaticEffect } from 'src/common/staticEffectType';

@Component({
  selector: 'app-static-effect-edit',
  templateUrl: './static-effect-edit.component.html',
  styleUrls: ['./static-effect-edit.component.css']
})
export class StaticEffectEditComponent implements OnInit {

  @Input() effect: StaticEffect;

  String = String

  particleType: string = ""
  effectType: string = ""

  R: number = 0;
  G: number = 0;
  B: number = 0;
  _hex: string = ""

  more_val: number[] = []

  particleTypes = {
    "BARRIER": "Barrier",
    "BLOCK_CRACK": "null",
    "BLOCK_DUST": "null",
    "CLOUD": "Cloud",
    "CRIT": "Critical Hit",
    "CRIT_MAGIC": "Magical Critical Hit",
    "DAMAGE_INDICATOR": "Damage Initcator",
    "DRAGON_BREATH": "Dragonbreath",
    "DRIP_LAVA": "null",
    "DRIP_WATER": "null",
    "ENCHANTMENT_TABLE": "Runes",
    "END_ROD": "null",
    "EXPLOSION_HUGE": "null",
    "EXPLOSION_LARGE": "null",
    "EXPLOSION_NORMAL": "null",
    "FALLING_DUST": "null",
    "FIREWORKS_SPARK": "null",
    "FLAME": "Flames",
    "FOOTSTEP": "Footstep",
    "HEART": "Hearts",
    "ITEM_CRACK": "null",
    "ITEM_TAKE": "null",
    "LAVA": "null",
    "MOB_APPEARANCE": "null",
    "NOTE": "Notes",
    "PORTAL": "null",
    "REDSTONE": "Colored Particle",
    "SLIME": "null",
    "SMOKE_LARGE": "null",
    "SMOKE_NORMAL": "null",
    "SNOW_SHOVEL": "null",
    "SNOWBALL": "null",
    "SPELL": "Spell",
    "SPELL_INSTANT": "null",
    "SPELL_MOB": "null",
    "SPELL_MOB_AMBIENT": "null",
    "SPELL_WITCH": "null",
    "SPIT": "null",
    "SUSPENDED": "null",
    "SUSPENDED_DEPTH": "null",
    "SWEEP_ATTACK": "null",
    "TOTEM": "null",
    "TOWN_AURA": "null",
    "VILLAGER_ANGRY": "null",
    "VILLAGER_HAPPY": "null",
    "WATER_BUBBLE": "null",
    "WATER_DROP": "null",
    "WATER_SPLASH": "Water Splash",
    "WATER_WAKE": "null",
  }

  effectTypeDefaults : Map<string, string[]> = new Map([
    ["single", ["single", "REDSTONE", "1", "1", "1"]], // TYPE EFFECT R G B
    ["circle", ["circle", "REDSTONE", "1", "20", "1", "1", "1"]], // TYPE EFFECT RADIUS RING_POINTS R G B
    ["pillar", ["pillar", "REDSTONE", "1", "10", "2", "10", "1", "1", "1"]] // TYPE EFFECT RADIUS RING_POINTS HEIGHT RINGS R G B
  ])

  effectTypes: any = {
    "single": "Single Point",
    "circle": "Circle",
    "pillar": "Pillar"
  }

  effectTypesExtraFields : Map<string, Array<string>> = new Map([
    ['single', []],
    ['circle', ['Radius', 'Ring Points']],
    ['pillar', ['Radius', 'Ring Points', 'Height', 'Rings']]
  ])

  constructor(private api:APIService) {
    this.effect = {
      DATA: "",
      ID: 0,
      POS: ""
    }
  }

  deconstructData(): void {
    let _temp: string[] = this.effect.DATA.split(">>");

    _temp = _temp.filter((i) => i != "")

    this.effectType = _temp.shift()!
    this.particleType = _temp.shift()!

    this.B = parseInt(_temp.pop()!)
    this.G = parseInt(_temp.pop()!)
    this.R = parseInt(_temp.pop()!)

    this._hex = this.rgbToHex(this.R, this.G, this.B);

    _temp.forEach((item) => {
      this.more_val.push(parseInt(item))
    })
  }

  reconstructData(): void {
    let data = ""

    data += this.effectType
    data += ">>"
    data += this.particleType
    data += ">>"

    this.more_val.forEach((value) => {
      data += value
      data += ">>"
    })

    data += this.R
    data += ">>"

    data += this.G
    data += ">>"

    data += this.B
    data += ">>"

    this.effect.DATA = data;    
  }

  ngOnInit(): void {
    this.deconstructData()
  }

  getEffectTypes(): Array<any> {
    return [...this.objectToMap(this.effectTypes)]
  }

  getParticleTypes(): Array<any> {
    return [...this.objectToMap(this.particleTypes)]
  }

  objectToMap(obj: any): Map<any, any> {
    return new Map(Object.keys(obj).map(key => /** @type [string, string] */([key, obj[key]])));
  };

  rgbToHex(r: number, g: number, b: number): string {
    return '#' + [r, g, b].map(x => {
      const hex = x.toString(16)
      return hex.length === 1 ? '0' + hex : hex
    }).join('')
  }

  hexToRGB() : void{
    let temp : number[] = this._hex.replace(/^#?([a-f\d])([a-f\d])([a-f\d])$/i
             ,(m, r, g, b) => '#' + r + r + g + g + b + b)
    .substring(1).match(/.{2}/g)!
    .map(x => parseInt(x, 16))

    this.R = temp[0]
    this.G = temp[1]
    this.B = temp[2]
  }

  onTypeChange() : void {
    this.more_val = [];

    let data : string = ""
    
    this.effectTypeDefaults.get(this.effectType)!.forEach((s) => {
      data += s;
      data += ">>"
    })

    this.effect.DATA = data

    this.deconstructData()     
  }

  onSubmit() : void{
    this.hexToRGB()
    this.reconstructData()
    this.api.saveStaticEffect(this.effect.ID, this.effect.DATA)
  }

}
