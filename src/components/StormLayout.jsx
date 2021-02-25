import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import { AppBar, Grid } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import SettingsRemoteIcon from '@material-ui/icons/SettingsRemote';
import RackAndBox from './RackAndBox';
import PersonIcon from '@material-ui/icons/Person';
import RemoteControl from './RemoteControl';
import TestVideoURL from '../assets/video/TestVideo.mp4';

const ThemeColor = "#8ea3ff";
const useStyles = makeStyles((theme) => ({
    list: {
        width: 160,
    },
    fullList: {
        width: 'auto',
    },
    toolBar: {
        height: "60px",
        display: "flex",
        alignItems: "center",
        flexDirection: "row",
        justifyContent: "space-between",
        backgroundColor: "transparent"
    },
    setTitle: {
        fontFamily: "stormHeader",
        fontSize: "20px",
        color: ThemeColor,
        fontWeight: 900
    },
    userDetailsOnAppBar: {
        BorderRight: "1px solid #d3d3d3"
    },
    stormTitle: {
        fontFamily: "'Averta','Helvetica Neue','Open Sans',Arial,sans-serif",
        fontSize: "40px",
        color: "#7793ed",
        letterSpacing: -.5,
        lineHeight: 1.33,
        fontWeight: 300,
        padding: 30
    },
    appDesc: {
        fontFamily: "'Averta','Helvetica Neue','Open Sans',Arial,sans-serif",
        color: "black",
        textAlign: "justify",
        padding: [0, 30, 0, 30]
    },
    toolbar: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
    },
    tvPanel: {
        width: "90%",
        height: 400,
        border: `4px solid ${ThemeColor}`,
        borderRadius: 5,
        boxShadow: "0 5px 17px rgba(0, 0, 0, 0.17), 0 0 0 1px rgba(210, 210, 210, 0.23)",
        textAlign: "center"
    },
    tvVideoPanel: {
        width: "98%",
        height: "86%",
        margin: "0 auto",
        marginTop: 8
    },
    remotePanel: {
        width: "99%",
        textAlign: "center",
        borderLeft: `2px solid ${ThemeColor}`
    },
    textCenter: {
        textAlign: "center"
    },
    userDetaillabel: {
        borderLeft: `4px solid ${ThemeColor}`,
        paddingRight: 10,
        display: "flex",
        alignItems: "center",
        color: ThemeColor,
        justifyContent: "center",
        width: "50%",
        float: "right"
    }
}));

export default function StormLayout() {


    const classes = useStyles();
    const [state, setState] = React.useState({
        top: false,
        left: false,
        bottom: false,
        right: false,
    });

    const toggleDrawer = (anchor, open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setState({ ...state, [anchor]: open });
    };


    const [teststate, setCount] = useState(2);
    return (
        <div>
            <AppBar className={classes.toolBar} elevation={1}>
                <Grid container space={1}>
                    <Grid item sm={4} >
                        <Button onClick={toggleDrawer('top', true)}><MenuIcon /></Button>
                    </Grid>
                    <Grid item sm={4} className={classes.textCenter}>
                        <Typography variant="h6" className={classes.setTitle}>
                            Storm Tool
                </Typography>
                    </Grid>
                    <Grid item sm={4} className={classes.textCenter}>
                        <div className={classes.userDetailsOnAppBar}>
                            <label className={classes.userDetaillabel}> <PersonIcon /><label> Hi, <span style={{ color: "black" }}>There</span></label><br />
                            </label>

                        </div>
                    </Grid>
                </Grid>
            </AppBar>
            <Drawer anchor={'top'} open={state['top']} onClose={toggleDrawer('top', false)}>
                <RackAndBox />
            </Drawer>
            <Drawer anchor={'right'} open={state['right']} onClose={toggleDrawer('right', false)}>
                sdsd
            </Drawer>
            <div>
                <main className={classes.content}>
                    <div className={classes.toolbar} />
                    <Grid container spacing={0} alignItems="center" justify="center" style={{ minHeight: '88vh', maxWidth: '100%' }}>
                        <Grid item sm={3}>
                            <div className={classes.stormTitle}>
                                Vz Tool
                        <Typography className={classes.appDesc} p={2}>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
                                    ut labore et dolore magna aliqua. Rhoncus dolor purus non enim praesent elementum
                                    facilisis leo vel. Risus at ultrices mi tempus imperdiet. Semper risus in hendrerit
                                    gravida rutrum quisque non tellus
                        </Typography>
                            </div>
                        </Grid>
                        <Grid item sm={6} style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
                            <div className={classes.tvPanel}>
                                <video  className={classes.tvVideoPanel} controls>
                                    <source src={TestVideoURL}/>
                                </video>
                            </div>
                        </Grid>
                        <Grid item sm={3}>
                            <div className={classes.remotePanel}>
                                <RemoteControl />
                            </div>
                        </Grid>
                    </Grid>
                </main>
            </div>

        </div >
    );
}