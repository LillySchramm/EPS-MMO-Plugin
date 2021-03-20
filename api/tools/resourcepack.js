const sql = require('../mysql/mysql')
const path = require('path');
const fs = require('fs')
const rimraf = require('rimraf');
const child_process = require("child_process");


const req_dir = ["/data/","temp/","assets/","minecraft/","mcpatcher/","cit/","misc_items"]

const pack_mcmeta = `
{
"pack": {
"pack_format": 3,
"description": "testing"
}
}
`

const item_default_property = `
type=item
items=minecraft:stained_glass_pane
nbt.display.Name=$$$$
`

async function genResourcePack(){
    await sql.query("SELECT * FROM `eps_items`.`items`").then((r) => {
        //Create dirs

        let base_dir = path.resolve(__dirname, '..')

        req_dir.forEach(dir => {
            base_dir += dir;

            if (!fs.existsSync(base_dir)){
                fs.mkdirSync(base_dir);
            }
        })   

        //Create base structure
        var base = path.resolve(__dirname, '..') + "/data/temp/"

        fs.writeFileSync(base + "pack.mcmeta", pack_mcmeta)

        //gen the items
        var img_base_dir = base + "assets/minecraft/mcpatcher/cit/misc_items/"
        r.forEach(item => {
            fs.writeFile(img_base_dir + item.ID + ".png", item.ICON, 'base64', function(err) {
                if(err){
                    console.log(err);
                }                
            });       
            
            fs.writeFile(img_base_dir + item.ID + ".properties", item_default_property.replace("$$$$", item.NAME), function(err) {
                if(err){
                    console.log(err);
                }                
            });           
        });       
        
        //gen zip
        new Promise(resolve => setTimeout(resolve, 100)).then(() =>{
            child_process.execSync(`zip -r ` + path.resolve(__dirname, '..') + "/data/" + `pack.zip *`, {
                cwd: base + ""
            });  
        });             
 
        //Clear temp
        new Promise(resolve => setTimeout(resolve, 100)).then(() =>{
            rimraf(path.resolve(__dirname, '..') + "/data/temp/", ()=>{})
        })       
        

   })
}
module.exports = {genResourcePack}