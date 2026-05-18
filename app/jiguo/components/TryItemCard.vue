<template>
	<view>
		<view class="try-item-card-warp">
			<view class='try-item-image'>
				<image :src="image" :style="'height:'+imageHeight"></image>
			</view>
			<view :class="'try-item-category '+(category=='大众试用'?'all':'tys')">{{category=='大众试用'?'首发':category}}</view>
			<view class="cool-item-title">{{title}}</view>
			<view :class="'try-item-price-quantity '+status">
				<view class="try-item-price">{{price?price:"暂无"}}</view>
				<view class="try-item-quantity">{{quantity}}台</view>
			</view>
			<view class="try-item-status">
				<view class="try-item-apply-count">
					<text>{{applyCount}}</text>申请
				</view>
			</view>
			<view v-if="status=='apply'" class="status apply">
				剩余天数{{remainDays}}天
			</view>
			<view v-else-if="status === 'try'" class="status try">
				查看试用名单
			</view>
			<view v-else class="status end">报告数量：{{reportCount}}</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "TryItemCard",
		data() {
			return {

			};
		},
		props: {
			id: {
				type: Number,
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
			quantity: {
				type: Number,
				required: true
			},
			price: {
				type: Number,
				required: true
			},
			category: {
				type: String,
				required: true
			},
			reportCount: {
				type: Number,
				required: false
			},
			applyCount: {
				type: Number,
				required: true,
				default: 0
			},
			remainDays: {
				type: Number,
				required: false,
			},
			status: {
				type: String,
				required: true
			},
			imageHeight: {
				type: String,
				required: false,
				default: "320rpx"
			}
		}
	}
</script>

<style scoped lang="scss">
	.try-item-card-warp {
		border-radius: 20rpx;
		position: relative;

		.try-item-image {
			width: 100%;

			image {
				width: 100%;
				border-radius: 20rpx 20rpx 0 0;
			}

			;
		}

		.try-item-category {
			position: absolute;
			top: 0;
			right: 0;
			padding: 10rpx;
		}

		.try-item-category.all {
			background-color: red;
			color: #fff;
			border-radius: 0 20rpx 0 20rpx;
		}

		.try-item-category.tys {
			background-color: yellow;
			color: darksalmon;
		}

		.try-item-title {
			font-size: 40rpx;
			font-weight: bold;
		}

		.try-item-price-quantity {
			width: 240rpx;
			margin: 10rpx auto;
			display: flex;
			justify-content: space-evenly;

			view {
				height: 40rpx;
				line-height: 40rpx;
				border: 2rpx solid red;
				border-radius: 10rpx;
				padding: 10rpx;
			}

			// view:first-child {
			// 	margin-right: 10rpx;
			// }
		}

		.try-item-apply-count {
			width: 240rpx;
			text-align: center;
			margin: 10rpx auto;

			text {
				color: red;
			}
		}

		.try-item-price-quantity.apply view {
			color: red;
			border-color: red;
		}
		.try-item-price-quantity.try view {
			color: green;
			border-color: green;
		}
		.try-item-price-quantity.end view {
			color: gray;
			border-color: gray;
		}
		.try-item-status {
			.try-item-apply-count {
				width: 240rpx;
				text-align: center;
				margin: 10rpx auto;

				text {
					color: red;
				}
			}

			.status {
				width: 240rpx;
				text-align: center;
				margin: 10rpx auto;
			}

			.apply {
				color: red;
			}

			.try {
				color: green;
			}

			.end {
				color: gray;
			}
		}
	}
</style>