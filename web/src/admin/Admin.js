import React, { Component } from "react";

import "admin/Admin.css";

class Admin extends Component {
  constructor(props) {
    super(props);

    const { company } = this.props.login.user;
    this.state = {
      company: {
        domain: company.domain,
        name: company.name || "",
        models: company.models
      },
      newModel: false,
      newModelName: ""
    };
  }

  render() {
    return (
      <div>
        <form class="pure-form pure-form-aligned">
          <fieldset>
            <div class="pure-control-group">
              <label for="company-domain">Domain</label>
              <input
                id="company-domain"
                disabled={true}
                value={this.state.company.domain}
              />
            </div>
            <div class="pure-control-group">
              <label for="campany-name">Name:</label>
              <input
                id="campany-name"
                value={this.state.company.name}
                onChange={evt =>
                  this.setState({
                    company: { ...this.state.company, name: evt.target.value }
                  })
                }
              />
            </div>
            <div class="pure-controls">
              <p>
                Models (<span
                  className="click"
                  onClick={() =>
                    this.setState({
                      newModel: true,
                      newModelName: ""
                    })
                  }
                >
                  +
                </span>):
              </p>
              <ul>{this.renderModels()}</ul>
              <p>
                <button
                  type="button"
                  className="pure-button pure-button-primary"
                  onClick={() => this.save()}
                >
                  Save!
                </button>
              </p>
            </div>
          </fieldset>
        </form>
        {this.state.newModel ? this.renderNewModel() : ""}
      </div>
    );
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
        <div
          className="overlay"
          onClick={() => this.setState({ newModel: false })}
        />
      </div>
    );
  }

  renderModels() {
    const { models } = this.state.company;
    if (models.length === 0) {
      return <li>No model added</li>;
    }

    return models.map(model => {
      return (
        <li>
          {model.fileId} (<span
            className="click"
            onClick={() => this.setState({})}
          >
            x
          </span>)
        </li>
      );
    });
  }

  addModel() {
    const { models } = this.state.company;
    models.push({ fileId: this.state.newModelName });
    this.setState({
      company: { ...this.state.company, models },
      newModel: false
    });
  }

  save() {
    const { company } = this.props.login.user;
    const data = this.state.company;
    this.props
      .fetch(company.id, "PUT", data)
      .then(async r => {
        const data = await r.json();
        console.log(data);
      })
      .catch(e => console.log(e));
  }
}

export default Admin;
