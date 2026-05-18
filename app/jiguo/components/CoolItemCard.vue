<template>
	<view class="cool-item-card-warp">
		<view class='cool-item-image'>
			<image :src="image"></image>
		</view>
		<view class="cool-item-name">{{name}}</view>
		<view class="cool-item-title">{{title}}</view>
		<view class="cool-item-price-thumb">
			<view class="cool-item-price">￥ {{price?price:"暂无"}}</view>
			<view class="cool-item-comment">
				<text class="iconfont icon-pinglun"></text>
				<text>{{commentCount}}</text>
			</view>
			<view class="cool-item-thumb">
				<text :class="'iconfont icon-dianzan '+(isThumb?'active':'')" 
				@tap.stop="doThumb"></text>
				<text>{{thumbCount}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "CoolItemCard",
		data() {
			return {
				//tCount: this.$props.thumbCount,
				
			};
		},
		methods: {
			doThumb(){
			this.$emit("clickthumb");
			}
		},
		props: {
			id: {
				type: Number,
				required: true
			},
			isThumb: {
				type: Boolean,
				required: false,
				default: false
			},
			name: {
				type: String,
				required: true
			},
			title: {
				type: String,
				required: true
			},
			image: {
				type: String,
				required: true
			},
			price: {
				type: Number,
				required: false
			},
			thumbCount: {
				type: Number,
				required: false,
				default: 0
			},
			commentCount: {
				type: Number,
				required: false,
				default: 0
			}
		}

	}
</script>

<style>
	.active {
		color: red;
	}

	.cool-item-image image {
		width: 100%;
	}

	.cool-item-name {
		font-size: 38rpx;
		width: 92%;
		margin: 6rpx 4% 0;
	}

	.cool-item-title {
		font-size: 28rpx;
		color: gray;
		width: 92%;
		margin: 6rpx 4% 0;
	}

	.cool-item-price-thumb {
		width: 92%;
		margin: 20rpx 4%;
	}

	.cool-item-price {
		color: red;
		font-size: 32rpx;
		float: left;
	}

	.cool-item-thumb {
		font-size: 28rpx;
		color: gray;
		float: right;
		margin-right: 10rpx;
	}

	.cool-item-comment {
		font-size: 28rpx;
		color: gray;
		float: right;
	}

	.cool-item-price-thumb::after {
		content: "";
		display: block;
		clear: both;
	}
</style>