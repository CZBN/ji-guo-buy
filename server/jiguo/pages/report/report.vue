<template>
	<view class="report-page">
		<view class="report-item" v-for="item in reports" :key="item.title">
			<image :src="buildImageUrl(item.image)" mode="aspectFill"></image>
			<view class="body">
				<view class="title">{{item.title}}</view>
				<view class="summary">{{item.summary}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imageUrl: "",
				reports: [
					{ image: "re1.jpg", title: "智能硬件深度体验报告", summary: "从外观、性能、续航和使用场景记录真实体验。" },
					{ image: "re3.jpg", title: "热门数码产品横向对比", summary: "围绕价格、功能与适用人群做出购买建议。" },
					{ image: "re4.jpg", title: "试用活动用户反馈精选", summary: "汇总用户试用后的亮点、问题和改进建议。" }
				]
			}
		},
		onLoad() {
			this.imageUrl = getApp().globalData.imageUrl;
		},
		methods: {
			buildImageUrl(imagePath) {
				return this.imageUrl + "/" + imagePath;
			}
		}
	}
</script>

<style scoped>
	.report-page {
		min-height: 100vh;
		background: #f7f7f7;
		padding: 24rpx;
	}

	.report-item {
		background: #fff;
		border-radius: 12rpx;
		overflow: hidden;
		margin-bottom: 24rpx;
	}

	.report-item image {
		width: 100%;
		height: 320rpx;
		background: #eee;
	}

	.body {
		padding: 24rpx;
	}

	.title {
		font-size: 34rpx;
		font-weight: 700;
		color: #222;
	}

	.summary {
		margin-top: 14rpx;
		font-size: 27rpx;
		line-height: 1.6;
		color: #666;
	}
</style>
