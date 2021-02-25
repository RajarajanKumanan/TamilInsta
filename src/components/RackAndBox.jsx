import React from 'react';
import PropTypes from 'prop-types';
import SwipeableViews from 'react-swipeable-views';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Zoom from '@material-ui/core/Zoom';
import { green } from '@material-ui/core/colors';
import Box from '@material-ui/core/Box';
import { Grid } from '@material-ui/core';
import Paper from '@material-ui/core/Paper';


function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <Typography
            component="div"
            role="tabpanel"
            hidden={value !== index}
            id={`action-tabpanel-${index}`}
            aria-labelledby={`action-tab-${index}`}
            {...other}
        >
            {value === index && <Box p={4}>{children}</Box>}
        </Typography>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

function a11yProps(index) {
    return {
        id: `action-tab-${index}`,
        'aria-controls': `action-tabpanel-${index}`,
    };
}

const useStyles = makeStyles((theme) => ({
    root: {
        backgroundColor: theme.palette.background.paper,
        position: 'relative',
        minHeight: 200,
    },
    fab: {
        position: 'absolute',
        bottom: theme.spacing(2),
        right: theme.spacing(2),
    },
    fabGreen: {
        color: theme.palette.common.white,
        backgroundColor: green[500],
        '&:hover': {
            backgroundColor: green[600],
        },
    },
    paper: {
        margin: theme.spacing(1),
    },
    svg: {
        width: 100,
        height: 100,
    },
    polygon: {
        fill: theme.palette.common.white,
        stroke: theme.palette.divider,
        strokeWidth: 1,
    },
    RackDesign: {
        width: "90%",
        height: 70,
        textAlign: "center",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "linear-gradient(to bottom, #7793ed, #7590e7, #728de2, #708adc, #6e87d7)"
    }
}));



const Rackboxes = () => {
    const classes = useStyles();
    const theme = useTheme();
    const [value, setValue] = React.useState(0);

    const [rack1, setRackOne] = React.useState([
        { id: 1, BoxName: "Box 1", selected: false, class_Name:"boxOne" },
        { id: 2, BoxName: "Box 2", selected: false, class_Name:"boxOne"  },
        { id: 3, BoxName: "Box 3", selected: false, class_Name:"boxOne"  },
        { id: 4, BoxName: "Box 4", selected: false, class_Name:"boxOne"  }
    ]);

    const changeInput = (e) => {
        rack1.map(items => items.id === parseInt(e.target.value) && (items.selected = e.target.checked));
        setRackOne([...rack1], rack1);
    }


    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleChangeIndex = (index) => {
        setValue(index);
    };

    return (
        <div className={classes.root}>
            <AppBar position="static" color="default">
                <Tabs
                    value={value}
                    onChange={handleChange}
                    indicatorColor="secondary"
                    textColor="secondary"
                    variant="fullWidth">
                    <Tab label="Rack 1" {...a11yProps(0)} />
                    <Tab label="Rack 2" {...a11yProps(1)} />
                    <Tab label="Rack 3" {...a11yProps(2)} />
                    <Tab label="Rack 4" {...a11yProps(4)} />
                </Tabs>
            </AppBar>
            <SwipeableViews
                axis={theme.direction === 'rtl' ? 'x-reverse' : 'x'}
                index={value}
                onChangeIndex={handleChangeIndex}>
                <TabPanel value={value} index={0} dir={theme.direction}>
                    <Grid container space={4}>
                        {rack1.map(items => {
                            return (
                                <Grid item sm={3} key={items.id}>
                                    <Zoom in={value == 0} style={{ transitionDelay: value == 0 ? (1 * 150) + 'ms' : '0ms' }}>
                                        <Paper elevation={2} className={classes.RackDesign}>
                                            <input type="checkbox"
                                                onChange={changeInput}
                                                value={items.id}
                                                id={items.id}
                                                checked={items.selected} />&nbsp; <label for={items.id}>{items.BoxName}</label>
                                        </Paper>
                                    </Zoom>
                                </Grid>
                            )
                        })}

                    </Grid>

                </TabPanel>
                <TabPanel value={value} index={1} dir={theme.direction}>
                    <Grid container space={4}>
                        {[0, 1, 2, 3].map((boxNumber) =>
                            < Grid item sm={3} key={boxNumber}>
                                <Zoom in={value == 1} style={{ transitionDelay: value == 1 ? (boxNumber * 250) + 'ms' : '0ms' }}>
                                    <Paper elevation={4} className={classes.paper}>
                                        <svg className={classes.svg}>
                                            <polygon points="0,100 50,00, 100,100" className={classes.polygon} />
                                        </svg>
                                    </Paper>
                                </Zoom>
                            </Grid >
                        )
                        }
                    </Grid>
                </TabPanel>
                <TabPanel value={value} index={2} dir={theme.direction}>
                    <Grid container space={4}>
                        {[0, 1, 2, 3].map((boxNumber) =>
                            < Grid item sm={3} key={boxNumber}>
                                <Zoom in={value == 2} style={{ transitionDelay: value == 2 ? (boxNumber * 250) + 'ms' : '0ms' }}>
                                    <Paper elevation={4} className={classes.paper}>
                                        <svg className={classes.svg}>
                                            <polygon points="0,100 50,00, 100,100" className={classes.polygon} />
                                        </svg>
                                    </Paper>
                                </Zoom>
                            </Grid >
                        )
                        }
                    </Grid>
                </TabPanel>
                <TabPanel value={value} index={3} dir={theme.direction}>
                    <Grid container space={4}>
                        {[0, 1, 2, 3].map((boxNumber) =>
                            < Grid item sm={3} key={boxNumber}>
                                <Zoom in={value == 3} style={{ transitionDelay: value == 3 ? (boxNumber * 250) + 'ms' : '0ms' }}>
                                    <Paper elevation={4} className={classes.paper}>
                                        <svg className={classes.svg}>
                                            <polygon points="0,100 50,00, 100,100" className={classes.polygon} />
                                        </svg>
                                    </Paper>
                                </Zoom>
                            </Grid >
                        )
                        }
                    </Grid>
                </TabPanel>
            </SwipeableViews>
        </div>
    );
}

export default Rackboxes;