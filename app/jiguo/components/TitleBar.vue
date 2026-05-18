<template>
	<view class="title-bar-wrap">
		<view :class="'my-icon iconfont ' +icon"></view>
		<view class="title-bar-text">{{title}}</view>
		<view class="title-bar-more" @tap="gotoPage">更多 ></view>
	</view>
</template>

<script>
	export default {
		name: "TitleBar",
		data() {
			return {

			};
		},
		methods: {
			gotoPage() {
				if (this.isTab) {
					uni.switchTab({
						url: this.Page
					})
				} else {
					uni.navigateTo({
						url: this.Page
					})
				}
			}
		},
		props: {
			title: String,
			Page: String,
			icon: String,
			isTab: {
				type: Boolean,
				required: false,
				default: true
			}
		}
	}
</script>

<style>
	.title-bar-wrap {
		padding: 10rpx;
	}

	.title-bar-wrap .my-icon {
		padding: 6rpx;
		font-size: 60rpx;
		color: #3cc51f;
	}

	.title-bar-wrap .title-bar-text {
		font-size: 38rpx;
		font-weight: bold;
		padding: 15rpx;

	}

	.title-bar-wrap view {
		float: left;
	}

	.title-bar-wrap .title-bar-more {
		float: right;
		font-size: 24rpx;
		color: #3cc51f;
		position: relative;
		top: 30rpx
	}

	.title-bar-wrap::after {
		content: "";
		display: block;
		clear: both;
	}
</style>