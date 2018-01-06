import React, { Component } from "react";

import "admin/Admin.css";

class Admin extends Component {
  constructor(props) {
    super(props);

    const { company } = this.props.login.user;
    this.state = {
      domain: company.domain,
      name: company.name || '',
      models: company.models,
      newModel: false,
      newModelName: ''
    };
  }

  render() {
    return <div>
        <p>Domain: {this.state.domain}</p>
        <p>Name: <input value={this.state.name} onChange={evt => this.setState({ name: evt.target.value })} /></p>
        <div>
          <p>
            Models (<span className="click" onClick={() => this.setState({
                  newModel: true,
                  newModelName: ""
                })}>
              +
            </span>):
          </p>
          <ul>{this.renderModels()}</ul>
          <p><button onClick={() => this.save()}>Save!</button></p>
        </div>
        {this.state.newModel ? this.renderNewModel() : ""}
      </div>;
  }

  renderNewModel() {
    return (
      <div>
        <div className="newModel">
          <p>
            File Id:
            <input
              value={this.state.newModelName}
              onChange={evt =>
                this.setState({ newModelName: evt.target.value })
              }
            />
          </p>
          <p>
            <button onClick={() => this.addModel()}>Add!</button>
          </p>
        </div>
        <div className="overlay" onClick={() => this.setState({ newModel: false })} />
      </div>
    );
  }

  renderModels() {
    if (this.state.models.length === 0) {
      return <li>No model added</li>;
    }

    return this.state.models.map(model => {
      return <li>{model.fileId} (<span className="click" onClick={() => this.setState({})}>x</span>)</li>;
    });
  }

  addModel() {
    const { models } = this.state;
    models.push({ fileId: this.state.newModelName });
    this.setState({ models, newModel: false });
  }

  save() {
    const { company } = this.props.login.user;
    const data = { name: this.state.name };
    this.props.fetch(company.id + '/fields', 'PUT', data).then(async r => {
      const data = await r.json();
      console.log(data);
    }).catch(e => console.log(e));
  }
}

export default Admin;
