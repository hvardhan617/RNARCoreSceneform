import React, { Component } from 'react';
import { BottomNavigation, Text } from 'react-native-paper';
import { NativeModules } from 'react-native';
import AssetsScreen from './AssetsScreen';
const activityStarter = NativeModules.ActivityStarter;

const AssetsRoute = () => <AssetsScreen />;

const AugmentedFacesRoute = () => activityStarter.launchArFacesActivity();

const AnimatedModelsRoute = () => activityStarter.launchARAnimatedModelView();

export default class HomeScreen extends Component {
	constructor(props) {
		super(props);
		this.state = {
			index: 0,
			routes: [
				{ key: 'assets', title: '3d Models', icon: 'queue-music' },
				{ key: 'selfie', title: 'AR Selfie', icon: 'history' },
				{ key: 'animations', title: 'Animated Models', icon: 'history' }
			]
		};
	}

	_handleIndexChange = (index) => this.setState({ index });

	_renderScene = BottomNavigation.SceneMap({
		assets: AssetsRoute,
		selfie: AugmentedFacesRoute,
		animations: AnimatedModelsRoute
	});
	componentDidMount() {}
	render() {
		return (
			<BottomNavigation
				navigationState={this.state}
				onIndexChange={this._handleIndexChange}
				renderScene={this._renderScene}
			/>
		);
	}
}
