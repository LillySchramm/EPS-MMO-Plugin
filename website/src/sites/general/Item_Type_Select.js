import React from 'react';
import ReactDOM from 'react-dom';
import itemTypes from '../../tools/itemTypeDir';
import itemTypeDefaults from "../../tools/itemTypeDefaults";

class ItemTypeSelect extends React.Component{
    render(){
        let options = [];
        let id = 0;
        
        Object.entries(itemTypes).forEach(([key, value]) => {
            if(key == this.props.cur){
                options.push(<option value={key} selected="selected">{value}</option>);
            }else{
                options.push(<option value={key}>{value}</option>);
            }                
        });        

        return(
            <select name="type_option" id="type_option" e_id={id} onChange={this.props.handler}>
                {                    
                    options                
                }
            </select>
        );
    }
}

export default ItemTypeSelect
