import React, { Component } from "react";

class Generator extends Component {
  render() {
      return <div>
          Generator {JSON.stringify(this.props.login)} {JSON.stringify(this.props.fetch)}
        </div>;
  }
}

export default Generator;