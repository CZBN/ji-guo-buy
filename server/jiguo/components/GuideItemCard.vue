<template>
	<view class="guide-item-card-warp">
		<!-- 商品图片区域 -->
		<view class='guide-item-image'>
			<image :src="image" mode="aspectFill"></image>
		</view>
		<!-- 商品品牌名称 -->
		<view class="guide-item-name">{{name}}</view>
		<!-- 商品标题 -->
		<view class="guide-item-title">{{title}}</view>
		<!-- 价格和互动区域 -->
		<view class="guide-item-price-thumb">
			<view class="guide-item-price">￥ {{price?price:"暂无"}}</view>
			<view class="guide-item-interaction">
				<view class="guide-item-comment">
					<text class="iconfont icon-pinglun"></text>
					<text>{{commentCount}}</text>
				</view>
				<view class="guide-item-thumb">
					<text :class="'iconfont icon-dianzan '+(isThumb?'active':'')" @tap.stop="doThumb"></text>
					<text>{{tCount}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "GuideItemCard",
		data() {
			return {
				tCount: this.$props.thumbCount,
				isThumb: false
			};
		},
		methods: {
			doThumb() {
				if (this.isThumb) {
					uni.showToast({
						title: "取消点赞：" + this.id,
						icon: 'error'
					});
					this.tCount--;
				} else {
					uni.showToast({
						title: "点赞：" + this.id
					});
					this.tCount++;
				}
				this.isThumb = !this.isThumb;
				this.$emit("clickthumb", {
					id: this.$props.id,
					isThumb: this.isThumb
				});
			}
		},
		props: {
			id: {
				type: Number,
				required: true
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
	.guide-item-card-warp {
		background: #ffffff;
		border-radius: 20rpx;
		overflow: hidden;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.08);
		transition: all 0.3s ease;
		margin-bottom: 20rpx;
	}

	.guide-item-card-warp:active {
		transform: scale(0.98);
		box-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.12);
	}

	.guide-item-image {
		width: 100%;
		height: 300rpx;
		overflow: hidden;
	}

	.guide-item-image image {
		width: 100%;
		height: 100%;
		display: block;
	}

	.guide-item-name {
		font-size: 32rpx;
		font-weight: bold;
		color: #333333;
		padding: 20rpx 20rpx 8rpx;
		line-height: 1.2;
	}

	.guide-item-title {
		font-size: 26rpx;
		color: #666666;
		padding: 0 20rpx;
		line-height: 1.4;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		overflow: hidden;
		text-overflow: ellipsis;
		height: 72rpx;
	}

	.guide-item-price-thumb {
		padding: 20rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.guide-item-price {
		color: #ff3b30;
		font-size: 32rpx;
		font-weight: bold;
		flex: 1;
	}

	.guide-item-interaction {
		display: flex;
		align-items: center;
		gap: 20rpx;
	}

	.guide-item-thumb,
	.guide-item-comment {
		display: flex;
		align-items: center;
		gap: 8rpx;
		font-size: 24rpx;
		color: #999999;
	}

	.guide-item-thumb text:first-child,
	.guide-item-comment text:first-child {
		font-size: 28rpx;
	}

	.active {
		color: #ff3b30 !important;
	}

	/* 响应式调整 */
	@media (max-width: 750px) {
		.guide-item-image {
			height: 280rpx;
		}

		.guide-item-name {
			font-size: 30rpx;
			padding: 16rpx 16rpx 6rpx;
		}

		.guide-item-title {
			font-size: 24rpx;
			padding: 0 16rpx;
			height: 68rpx;
		}

		.guide-item-price-thumb {
			padding: 16rpx;
		}

		.guide-item-price {
			font-size: 30rpx;
		}
	}
</style>