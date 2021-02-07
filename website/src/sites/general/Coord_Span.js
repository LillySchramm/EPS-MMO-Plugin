import React from 'react';
import ReactDOM from 'react-dom';

const formater = require('../../tools/formater');

class Coord_Span extends React.Component {
    constructor(probs) {
        super(probs);
    }

    render() {
        return (
            <span class='Coord_Span'> X: <span class='X'> {this.props.pos.getCoord(0)} </span> Y: <span class='Y'> {this.props.pos.getCoord(1)} </span> Z: <span class='Z'> {this.props.pos.getCoord(2)} </span>  </span>
        );
    }

}

export default Coord_Span