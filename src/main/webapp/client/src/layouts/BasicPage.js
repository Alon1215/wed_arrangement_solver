import React, {Component} from "react";
import { MDBNavbar, MDBNavbarBrand, MDBNavbarNav, MDBNavbarToggler, MDBCollapse, MDBFooter,MDBNavItem, MDBNavLink, MDBContainer, MDBMask, MDBView } from 'mdbreact';
import { BrowserRouter as Router } from 'react-router-dom';
import NavBar from "../layouts/NavBar";
import PropTypes from "prop-types";
import Footer from "./Footer";


class BasicPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            collapse: false
        };
        this.onClick = this.onClick.bind(this);
    }

    onClick() {
        this.setState({
            collapse: !this.state.collapse,
        });
    }

    render() {

        return (
            <div>
                <header>
                    <NavBar isLoggedIn={this.props.isLoggedIn} isAdmin={this.props.isAdmin} isHomePage={this.props.isHomePage}/>
                    {this.props.belowNavBar}
                </header>

                <main>
                    {this.props.mainPart}
                </main>
                <footer>
                    <Footer/>
                </footer>

            </div>
        );
    }
}

// PropTypes
BasicPage.propTypes = {
    isLoggedIn: PropTypes.func.isRequired,
    isAdmin: PropTypes.func.isRequired,
    isHomePage: PropTypes.bool.isRequired,
    belowNavBar: PropTypes.object,
    mainPart:PropTypes.object.isRequired,
};


export default BasicPage;